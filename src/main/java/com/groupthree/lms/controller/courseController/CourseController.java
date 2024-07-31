package com.groupthree.lms.controller.courseController;

import com.groupthree.lms.dto.courseDto.CourseDTO;
import com.groupthree.lms.services.courseService.CourseService;
import com.groupthree.lms.services.userService.UserService;
import com.groupthree.lms.models.userModel.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);
    private final CourseService courseService;
    private final UserService userService;

    @Autowired
    public CourseController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @GetMapping
    public List<CourseDTO> getAllCourses() {
        logger.info("Fetching all courses");
        return courseService.getAllCourses();
    }

    @GetMapping("/user/{userId}")
    public List<CourseDTO> getCoursesByUserId(@PathVariable Long userId) {
        logger.info("Fetching courses for user with ID: {}", userId);
        return courseService.getCoursesByUserId(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long id) {
        logger.info("Fetching course with ID: {}", id);
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@RequestBody CourseDTO courseDTO, Authentication authentication) {
        logger.info("Creating a new course: {}", courseDTO);
        try {
            Long userId = null;
            if (authentication != null) {
                Object principal = authentication.getPrincipal();
                logger.info("Authentication principal class: {}", principal.getClass().getName());
                logger.info("Authentication principal toString: {}", principal.toString());

                if (principal instanceof User) {
                    userId = ((User) principal).getId();
                    logger.info("Extracted user ID from User object: {}", userId);
                } else if (principal instanceof UserDetails) {
                    String username = ((UserDetails) principal).getUsername();
                    userId = userService.getUserIdByUsername(username);
                    logger.info("Extracted user ID from UserDetails: {}", userId);
                } else if (principal instanceof String) {
                    String username = (String) principal;
                    userId = userService.getUserIdByUsername(username);
                    logger.info("Extracted user ID from String username: {}", userId);
                }
            }

            if (userId == null) {
                logger.error("Unable to extract user ID from Authentication object");
                return ResponseEntity.badRequest().build();
            }

            // Set the user ID in the courseDTO
            courseDTO.setUserId(userId);

            CourseDTO createdCourse = courseService.addCourse(courseDTO);
            logger.info("Course created successfully with ID: {}", createdCourse.getId());
            return ResponseEntity.ok(createdCourse);
        } catch (Exception e) {
            logger.error("Error creating course: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long id, @RequestBody CourseDTO courseDetails) {
        logger.info("Updating course with ID: {}", id);
        return ResponseEntity.ok(courseService.updateCourse(id, courseDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        logger.info("Deleting course with ID: {}", id);
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}

