package com.groupthree.lms.controller.courseController;

import com.groupthree.lms.dto.courseDto.TopicsDTO;
import com.groupthree.lms.services.courseService.TopicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topic")
public class TopicsController {

  private final TopicService topicService;

  public TopicsController(TopicService topicService) {
      this.topicService = topicService;
  }

    @PostMapping
    public ResponseEntity<TopicsDTO> createTopic(@RequestBody TopicsDTO topic) {
        TopicsDTO createdTopic = topicService.createTopic(topic);
        return ResponseEntity.ok(createdTopic);
    }

    @GetMapping
    public ResponseEntity<List<TopicsDTO>> getAllTopics() {
        List<TopicsDTO> topicsDTO = topicService.getAllTopics();
        return ResponseEntity.ok(topicsDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicsDTO> getTopicById(@PathVariable long id) {
        TopicsDTO topicsDTO = topicService.getTopicById(id);
        if (topicsDTO != null) {
            return ResponseEntity.ok(topicsDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopicById(@PathVariable long id) {
        topicService.deleteTopicById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicsDTO> updateTopic(@PathVariable long id, @RequestBody TopicsDTO topicDTO) {
        TopicsDTO updatedTopic = topicService.updateTopic(id, topicDTO);
        return ResponseEntity.ok(updatedTopic);
    }
}
