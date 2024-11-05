package com.example.btl_oop.controller;

import com.example.btl_oop.entity.User;
import com.example.btl_oop.model.request.user.*;
import com.example.btl_oop.model.response.user.ChangeInfoResponse;
import com.example.btl_oop.model.response.user.RegisterResponse;
import com.example.btl_oop.model.response.user.UserDto;
import com.example.btl_oop.service.EmailService;
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
import java.util.Random;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

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
            String roleLength = userDetails.getAuthorities().toString();
            model.addAttribute("role", roleLength.substring(1, roleLength.length() - 1));
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

    @GetMapping("/changePassword")
    public String changePassword(Authentication authentication, Model model) {
        if (authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            UserDto userDto = userService.findUserByUsername(authentication.getName());
            model.addAttribute("username", userDto.getUsername());
            String roleLength = userDetails.getAuthorities().toString();
            model.addAttribute("role", roleLength.substring(1, roleLength.length() - 1));
            sendEmail(model, userDto);
        }
        return "user/changePassword";
    }

    private String encodeEmail(String email) {
        StringBuilder encodedEmail = new StringBuilder();
        encodedEmail.append(email.substring(0,5)).append("*".repeat(email.length() - 15))
                .append("@gmail.com");
        return encodedEmail.toString();
    }

    private String genOTP() {
        StringBuilder otp = new StringBuilder();
        Random rd = new Random();
        otp.append(rd.nextInt(10)).append(rd.nextInt(10)).append(rd.nextInt(10))
                .append(rd.nextInt(10)).append(rd.nextInt(10)).append(rd.nextInt(10));
        return otp.toString();
    }

    @PostMapping("/changePassword")
    public String changePassword(@ModelAttribute ChangePasswordRequest request, Authentication authentication) {
        if (authentication != null) {
            userService.changePassword(request, authentication.getName());
        }
        return "redirect:/user/profile";
    }

    @GetMapping("/retrievePassword")
    public String retrievePassword() {
        return "user/enterUsername";
    }

    @PostMapping("/retrievePassword")
    public String retrievePassword(@RequestParam(name = "username") String username, Model model) {
        UserDto userDto = userService.findUserByUsername(username.toLowerCase());
        if (userDto == null) {
            model.addAttribute("errorBE", "Tên đăng nhập không tồn tại!");
            return "user/enterUsername";
        }
        sendEmail(model, userDto);
        return "user/createNewPassword";
    }

    private void sendEmail(Model model, UserDto userDto) {
        String email = userDto.getEmail();
        String encodedEmail = encodeEmail(email);
        model.addAttribute("email", encodedEmail);
        String otp = genOTP();
        model.addAttribute("trueOTP", otp);
        emailService.sendEmail(email, "Mã bảo mật tài khoản của bạn", "<body style=\"font-family: Arial, sans-serif;\">\n" +
                "    <div style=\"max-width: 600px; margin: auto; padding: 20px; border: 1px solid #e0e0e0;\">\n" +
                "        <h2 style=\"font-size: 24px; margin-bottom: 10px;\">Mã bảo mật tài khoản của bạn</h2>\n" +
                "        <p style=\"margin: 10px 0;\">Xin chào "+userDto.getUsername()+ ",</p>\n" +
                "        <p style=\"margin: 10px 0;\">Mã bảo mật của bạn là:</p>\n" +
                "        <div style=\"font-size: 24px; font-weight: bold; padding: 10px; background-color: #f0f0f0; text-align: center; margin: 20px 0;\">\n" +
                otp +
                "        </div>\n" +
                "        <p style=\"margin: 10px 0;\">Để xác nhận danh tính của bạn trên Facebook, chúng tôi cần xác minh địa chỉ email của bạn. Hãy dán mã này vào trình duyệt. Đây là mã dùng một lần.</p>\n" +
                "        <p style=\"margin: 10px 0;\">Cảm ơn bạn!<br>Đội ngũ bảo mật của nhóm 9</p>\n" +
                "    </div>\n" +
                "</body>");
    }

    @PostMapping("/createNewPassword")
    public String saveNewPassword(@NonNull @ModelAttribute CreateNewPasswordRequest request) {
        userService.createNewPassword(request);
        return "redirect:/user/login";
    }
}
