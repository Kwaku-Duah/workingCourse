package com.groupthree.lms.services.courseService;

import com.groupthree.lms.dto.courseDto.CourseDTO;
import com.groupthree.lms.dto.courseDto.ModuleDTO;
import com.groupthree.lms.models.courseModel.Course;
import com.groupthree.lms.models.courseModel.Module;
import com.groupthree.lms.repositories.courseRepository.ModuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;
    private final CourseService courseService;

    public ModuleService(ModuleRepository syllabusRepository, CourseService courseService) {
        this.moduleRepository = syllabusRepository;
        this.courseService = courseService;
    }

    public List<ModuleDTO> getAllModules() {
        return moduleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ModuleDTO getModuleById(Long id) {
        return convertToDTO(moduleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Module not found with ID: ")));
    }

    public ModuleDTO saveModule(ModuleDTO moduleDTO) {
        if (moduleDTO.getCourse() == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        CourseDTO courseDTO = courseService.getCourseById(moduleDTO.getCourse().getId());
        Course course = courseService.convertToEntity(courseDTO);
        Module module = convertToEntity(moduleDTO);
        module.setCourse(course);
        Module savedModule = moduleRepository.save(module);
        return convertToDTO(savedModule);
    }

    public void deleteModule(Long id) {
        moduleRepository.deleteById(id);
    }

    public ModuleDTO updateModule(Long id, ModuleDTO moduleDTO) {
        Module existingModule = moduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Module not found with ID: " + id));

        existingModule.setTitle(moduleDTO.getTitle());
        existingModule.setDescription(moduleDTO.getDescription());

        if (moduleDTO.getCourse() != null) {
            CourseDTO courseDTO = courseService.getCourseById(moduleDTO.getCourse().getId());
            Course course = courseService.convertToEntity(courseDTO);
            existingModule.setCourse(course);
        }

        if (moduleDTO.getTopic() != null) {
            existingModule.setTopic(moduleDTO.getTopic());
        }

        Module updatedModule = moduleRepository.save(existingModule);

        return convertToDTO(updatedModule);
    }

    private ModuleDTO convertToDTO(Module module) {
        return new ModuleDTO(module.getId(), module.getTitle(), module.getDescription(),
                module.getCourse(), module.getTopic());
    }

    public Module convertToEntity(ModuleDTO moduleDTO) {
        return new Module(moduleDTO.getId(), moduleDTO.getTitle(), moduleDTO.getDescription(),
                moduleDTO.getCourse(), moduleDTO.getTopic());
    }


}

