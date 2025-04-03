package com.example.questioncollection.service;

import com.example.questioncollection.dto.AnswerRequest;
import com.example.questioncollection.dto.QuestionDto;
import com.example.questioncollection.dto.QuestionRequest;
import com.example.questioncollection.entity.Question;
import org.springframework.data.domain.Page;

import java.util.List;

public interface QuestionService {
    
    QuestionDto createQuestion(QuestionRequest request, String imagePath, String imageOriginalName);
    
    QuestionDto getQuestionById(Long id);
    
    Page<QuestionDto> getAllQuestions(int page, int size);
    
    Page<QuestionDto> getQuestionsByStatus(Question.QuestionStatus status, int page, int size);
    
    List<QuestionDto> getQuestionsByStudentName(String studentName);
    
    QuestionDto answerQuestion(Long id, AnswerRequest answerRequest);
    
    void deleteQuestion(Long id);
} 