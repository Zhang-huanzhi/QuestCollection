package com.example.questioncollection.dto;

import com.example.questioncollection.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerRequest {
    
    @NotBlank(message = "回答内容不能为空")
    private String answer;
    
    @NotNull(message = "状态不能为空")
    private Question.QuestionStatus status;
} 