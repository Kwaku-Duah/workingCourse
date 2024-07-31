package com.groupthree.lms.models.courseModel;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class LearningMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topics topics;

    @ManyToOne
    @JoinColumn(name = "topic")
    @JsonBackReference
    private Topics topic;

    private String title;
    private String type;
    private String s3FileUrl;  // Store the S3 file URL here
    private int orderIndex;

    // Getters and setters
}
