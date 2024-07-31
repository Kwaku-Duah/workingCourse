package com.groupthree.lms.repositories.courseRepository;

import com.groupthree.lms.models.courseModel.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
