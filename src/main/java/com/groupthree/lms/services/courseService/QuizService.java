package com.groupthree.lms.services.courseService;

import com.groupthree.lms.dto.courseDto.QuizDTO;
import com.groupthree.lms.models.courseModel.Quiz;
import com.groupthree.lms.repositories.courseRepository.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public List<QuizDTO> getAllQuiz() {
        return quizRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public QuizDTO getQuizById(Long id) {
        return convertToDTO(quizRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found")));
    }

    public QuizDTO addQuiz(QuizDTO quizDTO) {
        Quiz quiz = convertToEntity(quizDTO);
            Quiz savedquiz = quizRepository.save(quiz);
        return convertToDTO(savedquiz);
    }

    public void deleteQuizById(Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Homework not found with id " + id));

        quizRepository.delete(quiz);
    }
    public QuizDTO updateQuiz(Long id, QuizDTO quizDTO) {

        Quiz existingQuiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id " + id));

        existingQuiz.setQuiz(quizDTO.getQuiz());
        existingQuiz.setTopic(quizDTO.getTopic());

        Quiz updatedQuiz = quizRepository.save(existingQuiz);

        return convertToDTO(updatedQuiz);
    }

    public Quiz convertToEntity(QuizDTO quizDTO) {
        if (quizDTO == null) {
            return null;
        }
        Quiz quiz = new Quiz();
        quiz.setId(quizDTO.getId());
        quiz.setQuiz(quizDTO.getQuiz());
        quiz.setTopic(quizDTO.getTopic());
        return quiz;
    }
    public QuizDTO convertToDTO(Quiz quiz) {
        if (quiz == null) {
          return null;
    }
    return new QuizDTO(quiz.getId(), quiz.getQuiz(), quiz.getTopic());
    }
}
