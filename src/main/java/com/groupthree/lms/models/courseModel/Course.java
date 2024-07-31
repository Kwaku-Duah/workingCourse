package com.groupthree.lms.models.courseModel;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.groupthree.lms.models.userModel.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String syllabus;
    private String schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Module> modules;

    // Constructor excluding the id
    public Course(String title, String description, String syllabus, String schedule, User user, List<Module> modules) {
        this.title = title;
        this.description = description;
        this.syllabus = syllabus;
        this.schedule = schedule;
        this.user = user;
        this.modules = modules;
    }
}
