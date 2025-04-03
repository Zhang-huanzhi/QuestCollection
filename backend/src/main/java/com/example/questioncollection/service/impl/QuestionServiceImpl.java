package com.example.questioncollection.service.impl;

import com.example.questioncollection.dto.AnswerRequest;
import com.example.questioncollection.dto.QuestionDto;
import com.example.questioncollection.dto.QuestionRequest;
import com.example.questioncollection.entity.Question;
import com.example.questioncollection.repository.QuestionRepository;
import com.example.questioncollection.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    @Override
    @Transactional
    public QuestionDto createQuestion(QuestionRequest request, String imagePath, String imageOriginalName) {
        Question question = new Question();
        question.setStudentName(request.getStudentName());
        question.setContent(request.getContent());
        question.setImagePath(imagePath);
        question.setImageOriginalName(imageOriginalName);
        question.setStatus(Question.QuestionStatus.PENDING);
        
        Question savedQuestion = questionRepository.save(question);
        return convertToDto(savedQuestion);
    }

    @Override
    public QuestionDto getQuestionById(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("问题不存在，ID: " + id));
        return convertToDto(question);
    }

    @Override
    public Page<QuestionDto> getAllQuestions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return questionRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(this::convertToDto);
    }

    @Override
    public Page<QuestionDto> getQuestionsByStatus(Question.QuestionStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return questionRepository.findByStatusOrderByCreatedAtDesc(status, pageable)
                .map(this::convertToDto);
    }

    @Override
    public List<QuestionDto> getQuestionsByStudentName(String studentName) {
        return questionRepository.findByStudentNameOrderByCreatedAtDesc(studentName)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public QuestionDto answerQuestion(Long id, AnswerRequest answerRequest) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("问题不存在，ID: " + id));
        
        question.setAnswer(answerRequest.getAnswer());
        question.setStatus(answerRequest.getStatus());
        
        Question updatedQuestion = questionRepository.save(question);
        return convertToDto(updatedQuestion);
    }

    @Override
    @Transactional
    public void deleteQuestion(Long id) {
        if (!questionRepository.existsById(id)) {
            throw new EntityNotFoundException("问题不存在，ID: " + id);
        }
        questionRepository.deleteById(id);
    }
    
    private QuestionDto convertToDto(Question question) {
        QuestionDto dto = new QuestionDto();
        dto.setId(question.getId());
        dto.setStudentName(question.getStudentName());
        dto.setContent(question.getContent());
        dto.setImagePath(question.getImagePath());
        dto.setImageOriginalName(question.getImageOriginalName());
        dto.setAnswer(question.getAnswer());
        dto.setStatus(question.getStatus());
        dto.setCreatedAt(question.getCreatedAt());
        dto.setUpdatedAt(question.getUpdatedAt());
        return dto;
    }
} 