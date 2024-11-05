package com.example.btl_oop.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/home")
public class PostController {

    @GetMapping("/news_1")
    public String notice(){
        return "news_1";
    }

    @GetMapping("/news_2")
    public String dang_song(){
        return "news_2";
    }
    @GetMapping("/nenthuenhaodau")
    public String post1(){
        return "nenthuenhaodau";
    }
    @GetMapping("/nhadephon")
    public String post2(){
        return "nhadephon";
    }
}
