package com.example.btl_oop.controller;

import com.example.btl_oop.model.request.room.RoomFilterDataRequest;
import com.example.btl_oop.model.response.room.RoomDto;
import com.example.btl_oop.service.RoomService;
import jakarta.validation.constraints.Null;
import com.example.btl_oop.entity.Room;
//import com.example.btl_oop.model.dto.RoomDTO;
import com.example.btl_oop.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.*;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("api/home")
public class HomeController {

    @Autowired
    private RoomService roomService;

    private static final int sizeOfPage = 4;

    private void func_common(Authentication authentication, Model model) {
        if (authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            model.addAttribute("username", userDetails.getUsername());
            String roleLength = userDetails.getAuthorities().toString();
            model.addAttribute("role", roleLength.substring(1, roleLength.length() - 1));
        }
    }

    @GetMapping
    public String home(Authentication authentication, Model model,
                       @ModelAttribute RoomFilterDataRequest request
            , @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
        func_common(authentication, model);
        Pageable pageable = PageRequest.of(pageNo - 1, sizeOfPage);
        List<RoomDto> roomList = roomService.getAllRoomByManyContraints(request, pageable);

        model.addAttribute("rooms", roomList);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPage", roomList.size() / sizeOfPage + 1);

        // Lưu trạng thái lọc khi chuyển trang
        model.addAttribute("request", request);
        return "index";
    }


    @GetMapping("/UserManagement")
    public String usermanagement() {
        return "UserManagement";
    }

    @GetMapping("/news_1")
    public String notice(Authentication authentication, Model model) {
        func_common(authentication, model);
        return "news_1";
    }

    @GetMapping("/news_2")
    public String dang_song(Authentication authentication, Model model) {
        func_common(authentication, model);
        return "news_2";
    }

    @GetMapping("/nenthuenhaodau")
    public String post1(Authentication authentication, Model model) {
        func_common(authentication, model);
        return "nenthuenhaodau";
    }

    @GetMapping("/nhadephon")
    public String post2(Authentication authentication, Model model) {
        func_common(authentication, model);
        return "nhadephon";
    }


}
