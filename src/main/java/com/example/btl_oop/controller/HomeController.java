package com.example.btl_oop.controller;

import com.example.btl_oop.model.request.room.RoomFilterDataRequest;
import com.example.btl_oop.model.response.room.RoomDto;
import com.example.btl_oop.service.RoomService;
import jakarta.validation.constraints.Null;
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

@Controller
@RequestMapping("api")
public class HomeController {

    @Autowired
    private RoomService roomService;

    private static int sizeOfPage = 4;

    @GetMapping("/home")
    public String home(Authentication authentication, Model model,
                       @ModelAttribute RoomFilterDataRequest request
    ,@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
        if (authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            model.addAttribute("username", userDetails.getUsername());
            String roleLength = userDetails.getAuthorities().toString();
            model.addAttribute("role", roleLength.substring(1, roleLength.length() - 1));
        }
        Pageable pageable = PageRequest.of(pageNo - 1, sizeOfPage);
        List<RoomDto> roomList = roomService.getAllRoomByManyContraints(request, pageable);
        model.addAttribute("rooms", roomList);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPage", roomList.size()/sizeOfPage + 1);

        // Lưu trạng thái lọc khi chuyển trang
        model.addAttribute("request", request);

        return "index";
    }
    @GetMapping("/home/UserManagement")
    public String usermanagement() {
        return "UserManagement";
    }
}
