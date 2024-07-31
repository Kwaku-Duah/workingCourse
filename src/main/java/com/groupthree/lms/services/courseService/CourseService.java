package com.groupthree.lms.services.courseService;

import com.groupthree.lms.dto.courseDto.CourseDTO;
import com.groupthree.lms.models.courseModel.Course;
import com.groupthree.lms.models.userModel.User;
import com.groupthree.lms.repositories.courseRepository.CourseRepository;
import com.groupthree.lms.repositories.userRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

   
    public CourseService(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CourseDTO> getCoursesByUserId(Long userId) {
        return courseRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CourseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return convertToDTO(course);
    }

    public CourseDTO addCourse(CourseDTO courseDTO) {
        Course course = convertToEntity(courseDTO);
        
        // Get authenticated user
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        course.setUser(user);
        Course savedCourse = courseRepository.save(course);
        return convertToDTO(savedCourse);
    }

    public CourseDTO updateCourse(Long id, CourseDTO courseDetails) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id " + id));

        course.setTitle(courseDetails.getTitle());
        course.setDescription(courseDetails.getDescription());
        course.setSchedule(courseDetails.getSchedule());
        course.setSyllabus(courseDetails.getSyllabus());
        course.setModules(courseDetails.getModules());

        Course updatedCourse = courseRepository.save(course);
        return convertToDTO(updatedCourse);
    }

    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id " + id));

        courseRepository.delete(course);
    }

    public CourseDTO convertToDTO(Course course) {
        return new CourseDTO(course.getId(), course.getTitle(), course.getDescription(),
                course.getSyllabus(), course.getSchedule(), course.getUser().getId(), course.getModules());
    }

    public Course convertToEntity(CourseDTO courseDTO) {
        User user = userRepository.findById(courseDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id " + courseDTO.getUserId()));

        return new Course(courseDTO.getTitle(), courseDTO.getDescription(),
                courseDTO.getSyllabus(), courseDTO.getSchedule(), user, courseDTO.getModules());
    }
}
