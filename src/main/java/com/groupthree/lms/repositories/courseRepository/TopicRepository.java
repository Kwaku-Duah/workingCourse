package com.groupthree.lms.repositories.courseRepository;

import com.groupthree.lms.models.courseModel.Topics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topics, Long> {
}
