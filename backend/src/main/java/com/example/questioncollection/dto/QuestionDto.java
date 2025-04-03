package com.example.questioncollection.dto;

import com.example.questioncollection.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {
    private Long id;
    
    @NotBlank(message = "学生姓名不能为空")
    private String studentName;
    
    @NotBlank(message = "问题内容不能为空")
    private String content;
    
    private String imagePath;
    
    private String imageOriginalName;
    
    private String answer;
    
    private Question.QuestionStatus status;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
} 