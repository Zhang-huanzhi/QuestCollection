package com.example.questioncollection.controller;

import com.example.questioncollection.dto.AnswerRequest;
import com.example.questioncollection.dto.ApiResponse;
import com.example.questioncollection.dto.QuestionDto;
import com.example.questioncollection.dto.QuestionRequest;
import com.example.questioncollection.entity.Question;
import com.example.questioncollection.service.FileStorageService;
import com.example.questioncollection.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final FileStorageService fileStorageService;

    @Autowired
    public QuestionController(QuestionService questionService, FileStorageService fileStorageService) {
        this.questionService = questionService;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<QuestionDto>> createQuestion(
            @RequestParam("studentName") String studentName,
            @RequestParam("content") String content,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        
        QuestionRequest request = new QuestionRequest();
        request.setStudentName(studentName);
        request.setContent(content);
        
        // 处理图片上传
        String imagePath = null;
        String imageOriginalName = null;
        
        if (image != null && !image.isEmpty()) {
            imagePath = fileStorageService.storeFile(image);
            imageOriginalName = image.getOriginalFilename();
        }
        
        // 创建问题并设置图片路径
        QuestionDto createdQuestion = questionService.createQuestion(request, imagePath, imageOriginalName);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("问题提交成功", createdQuestion));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<QuestionDto>> getQuestionById(@PathVariable Long id) {
        QuestionDto question = questionService.getQuestionById(id);
        return ResponseEntity.ok(ApiResponse.success(question));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<QuestionDto>>> getAllQuestions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<QuestionDto> questions = questionService.getAllQuestions(page, size);
        return ResponseEntity.ok(ApiResponse.success(questions));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<Page<QuestionDto>>> getQuestionsByStatus(
            @PathVariable Question.QuestionStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<QuestionDto> questions = questionService.getQuestionsByStatus(status, page, size);
        return ResponseEntity.ok(ApiResponse.success(questions));
    }

    @GetMapping("/student/{studentName}")
    public ResponseEntity<ApiResponse<List<QuestionDto>>> getQuestionsByStudentName(@PathVariable String studentName) {
        List<QuestionDto> questions = questionService.getQuestionsByStudentName(studentName);
        return ResponseEntity.ok(ApiResponse.success(questions));
    }

    @PutMapping("/{id}/answer")
    public ResponseEntity<ApiResponse<QuestionDto>> answerQuestion(
            @PathVariable Long id,
            @Valid @RequestBody AnswerRequest answerRequest) {
        QuestionDto answeredQuestion = questionService.answerQuestion(id, answerRequest);
        return ResponseEntity.ok(ApiResponse.success("问题已回答", answeredQuestion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.ok(ApiResponse.success("问题已删除", null));
    }
} 