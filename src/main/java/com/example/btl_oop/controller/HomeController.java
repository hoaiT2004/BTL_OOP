package com.example.btl_oop.controller;

import com.example.btl_oop.entity.Room;
import com.example.btl_oop.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import java.util.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api")
public class HomeController {

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping("/home")
    public String home(Authentication authentication, Model model) {
        if (authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            model.addAttribute("username", userDetails.getUsername());
            String roleLength = userDetails.getAuthorities().toString();
            model.addAttribute("role", roleLength.substring(1, roleLength.length() - 1));
        }
        List<Room> roomList = roomRepository.findRoomByIsApproval("true");
        model.addAttribute("rooms", roomList);
        return "index";
    }
    @GetMapping("/home/UserManagement")
    public String usermanagement() {
        return "UserManagement";
    }
    @GetMapping("/home/RoomManagement")
    public String roommanagement() {
        return "RoomManagement";
    }
}
