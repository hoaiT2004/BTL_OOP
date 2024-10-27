package com.example.btl_oop.controller;

import com.example.btl_oop.model.request.user.RegisterRequest;
import com.example.btl_oop.model.response.user.RegisterResponse;
import com.example.btl_oop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterRequest registerRequest){
        RegisterResponse userDto = userService.register(registerRequest);
        if(userDto != null){
            return "redirect:/user/login";
        }
        return "redirect:/user/register";
    }
}
