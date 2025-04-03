package com.example.questioncollection.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    
    String storeFile(MultipartFile file);
    
    byte[] getFile(String fileName);
} 