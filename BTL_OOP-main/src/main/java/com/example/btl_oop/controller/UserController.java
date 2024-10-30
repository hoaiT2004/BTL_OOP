package com.example.btl_oop.controller;

import com.example.btl_oop.entity.User;
import com.example.btl_oop.model.request.user.ChangeAvatarRequest;
import com.example.btl_oop.model.request.user.ChangeInfoRequest;
import com.example.btl_oop.model.request.user.RegisterRequest;
import com.example.btl_oop.model.response.user.ChangeInfoResponse;
import com.example.btl_oop.model.response.user.RegisterResponse;
import com.example.btl_oop.model.response.user.UserDto;
import com.example.btl_oop.service.UserService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String getAll(Model model, @RequestParam(name = "keyword") String keyword,
                         @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 10);
        List<UserDto> users = userService.getAllUser(keyword, pageable);
        model.addAttribute("users",users);
        return "user/list";
    }

    @GetMapping("/login")
    public String login(){
        return "user/login";
    }

    @GetMapping("/register")
    public String register(){
        return "user/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute RegisterRequest registerRequest){
        RegisterResponse userDto = userService.register(registerRequest);
        if(userDto != null){
            return "redirect:/user/login";
        }
        return "redirect:/user/register";
    }

    @GetMapping("/profile")
    public String profile(Authentication authentication, Model model) {
        if (authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            UserDto userDto = userService.findUserByUsername(userDetails.getUsername());
            model.addAttribute("username", userDto.getUsername());
            model.addAttribute("role", userDetails.getAuthorities());
            model.addAttribute("linkAvatar", userDto.getLinkAvatar());
            model.addAttribute("fullname", userDto.getFullname());
            model.addAttribute("email", userDto.getEmail());
            model.addAttribute("tel", userDto.getTel());
        }
        return "user/profile";
    }

    @PostMapping("/changeInfo")
    public String changeInfo(@NonNull @ModelAttribute ChangeInfoRequest request) {
        userService.changeInfo(request);
        return "redirect:/user/profile";
    }

    @PostMapping("/uploadFile")
    public String changeAvatar(@RequestParam(name = "linkAvatar") MultipartFile file, Authentication authentication) {
        if (authentication != null) {
            var request = ChangeAvatarRequest.builder()
                    .file(file)
                    .username(authentication.getName())
                    .build();
            userService.changeAvatar(request);
        }
        return "redirect:/user/profile";
    }
}
