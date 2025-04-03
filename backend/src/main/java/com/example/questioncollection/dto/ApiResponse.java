package com.example.questioncollection.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;
    private LocalDateTime timestamp = LocalDateTime.now();

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(HttpStatus.OK.value(), "操作成功", data, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(HttpStatus.OK.value(), message, data, LocalDateTime.now());
    }

    public static ApiResponse<Void> success() {
        return new ApiResponse<>(HttpStatus.OK.value(), "操作成功", null, LocalDateTime.now());
    }

    public static ApiResponse<Void> fail(String message) {
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), message, null, LocalDateTime.now());
    }

    public static ApiResponse<Void> fail(int status, String message) {
        return new ApiResponse<>(status, message, null, LocalDateTime.now());
    }
} 