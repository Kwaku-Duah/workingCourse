package com.groupthree.lms.services.courseService;

import com.groupthree.lms.dto.courseDto.TopicsDTO;
import com.groupthree.lms.models.courseModel.Module;
import com.groupthree.lms.models.courseModel.Topics;
import com.groupthree.lms.repositories.courseRepository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final ModuleService moduleService;

    public TopicService(TopicRepository topicRepository, ModuleService moduleService) {
        this.topicRepository = topicRepository;
        this.moduleService = moduleService;
    }

    public TopicsDTO createTopic(TopicsDTO topicDTO) {
        Long moduleId = topicDTO.getModule().getId();
        if (moduleId == null) {
            throw new IllegalArgumentException("Module ID cannot be null");
        }

        Module module = moduleService.convertToEntity(moduleService.getModuleById(moduleId));

        Topics topic = new Topics();
        topic.setName(topicDTO.getName());
        topic.setContent(topicDTO.getContent());
        topic.setConclusion(topicDTO.getConclusion());
        topic.setModule(module);

        Topics createdTopic = topicRepository.save(topic);
        return convertToDTO(createdTopic);
    }

    public List<TopicsDTO> getAllTopics() {
        return topicRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TopicsDTO getTopicById(Long id) {
        Optional<Topics> topic = topicRepository.findById(id);
        return topic.map(this::convertToDTO).orElse(null);
    }

    public void deleteTopicById(Long id) {
        topicRepository.deleteById(id);
    }
    public TopicsDTO updateTopic(Long id, TopicsDTO topicDTO) {

        Topics existingTopic = topicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Topic not found with ID: " + id));

        existingTopic.setName(topicDTO.getName());
        existingTopic.setContent(topicDTO.getContent());
        existingTopic.setConclusion(topicDTO.getConclusion());

        Long moduleId = topicDTO.getModule().getId();
        if (moduleId != null) {
            Module module = moduleService.convertToEntity(moduleService.getModuleById(moduleId));
            existingTopic.setModule(module);
        }

        Topics updatedTopic = topicRepository.save(existingTopic);
        return convertToDTO(updatedTopic);
    }



    private TopicsDTO convertToDTO(Topics topic) {
        return new TopicsDTO(
                topic.getId(),
                topic.getName(),
                topic.getContent(),
                topic.getConclusion(),
                topic.getModule(),
                topic.getQuizzes()
        );
    }
}
