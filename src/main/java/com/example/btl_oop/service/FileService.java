package com.example.btl_oop.service;

import org.springframework.web.multipart.MultipartFile;


public interface FileService {

    String uploadFile(MultipartFile file);
}
