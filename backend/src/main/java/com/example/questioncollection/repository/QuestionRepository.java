package com.example.questioncollection.repository;

import com.example.questioncollection.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    
    Page<Question> findAllByOrderByCreatedAtDesc(Pageable pageable);
    
    Page<Question> findByStatusOrderByCreatedAtDesc(Question.QuestionStatus status, Pageable pageable);
    
    List<Question> findByStudentNameOrderByCreatedAtDesc(String studentName);
} 