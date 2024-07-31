package com.groupthree.lms.controller.courseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.groupthree.lms.services.courseService.S3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/learning-materials")
public class LearningMaterialController {

    private static final Logger logger = LoggerFactory.getLogger(LearningMaterialController.class);

    @Autowired
    private S3Service s3Service;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
            }
    
            String fileName = s3Service.uploadFile(file);
     
            logger.info("File uploaded successfully: {}", fileName);
            return ResponseEntity.ok("File uploaded successfully: " + fileName);
        } catch (Exception e) {
            logger.error("Failed to upload file: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
        }
    }

    @DeleteMapping("/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        try {
            // Validate fileName if needed
            s3Service.deleteFile(fileName);
            // Remove file information from your database if needed
            logger.info("File deleted successfully: {}", fileName);
            return ResponseEntity.ok("File deleted successfully: " + fileName);
        } catch (Exception e) {
            logger.error("Failed to delete file: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete file: " + e.getMessage());
        }
    }
}
