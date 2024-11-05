package com.example.btl_oop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/home")
public class PostController {

    @GetMapping("/nhadephon")
    public String showHouseBeautyPage() {
        return "nhadephon"; // Tên của file HTML (không cần phần mở rộng .html)
    }
    @GetMapping("/nenthuenhaodau")
    public String showRentalAdvicePage() {
        return "nenthuenhaodau"; // Trả về tên file HTML (không cần phần mở rộng .html)
    }
}