package com.example.btl_oop.controller;

import java.util.List;

import com.example.btl_oop.model.dto.CommentDto;
import com.example.btl_oop.model.dto.UserDto;
import com.example.btl_oop.model.request.AppointmentRequest;
import com.example.btl_oop.model.dto.RoomDto;
import com.example.btl_oop.service.AppointmentService;
import com.example.btl_oop.service.CommentService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.data.domain.PageRequest;
import com.example.btl_oop.service.RoomService;
import com.example.btl_oop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("api/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private CommentService commentService;

    @PostMapping("/addroom")
    public String addRoom(@ModelAttribute RoomDto roomDto, Model model, Authentication auth,@RequestParam("images") List<MultipartFile> images ) {
        commonFunc(auth, model);
        roomService.addRoom(roomDto, images, auth);
        return "redirect:/api/room/addroom";
    }

    @GetMapping("/addroom")
    public String showAddRoom(@ModelAttribute RoomDto roomDto, Model model, Authentication auth) {
        commonFunc(auth, model);
        model.addAttribute("room", new RoomDto());
        return "addroom";
    }

    @GetMapping
    public String showDetailRoom(Authentication auth, @RequestParam(name = "room_id") String room_id, Model model) {
        RoomDto roomDto = roomService.getInfoRoomByRoom_Id(room_id);
        List<String>  imageDtos = roomService.getAllImagesByRoom_Id(room_id);
        model.addAttribute("room", roomDto);
        model.addAttribute("images", imageDtos);

        UserDto userDto = userService.getUserById(roomDto.getUser_id());
        model.addAttribute("userDto", userDto);

        List<CommentDto> commentDtos = commentService.getAllCommentsByRoom_id(Long.parseLong(room_id));
        model.addAttribute("comments", commentDtos);

        commonFunc(auth, model);
        return "room/details";
    }

    @GetMapping("/schedule")
    public String schedule(Authentication auth, Model model, @RequestParam(name = "room_id") String room_id) {
        commonFunc(auth, model);
        UserDto dto = userService.findUserByUsername(auth.getName());
        model.addAttribute("dto",dto);
        model.addAttribute("room_id", room_id);
        return "room/schedule";
    }

    @PostMapping("/schedule")
    public String createAppointment(@ModelAttribute AppointmentRequest request, Authentication auth, Model model) {
        commonFunc(auth, model);
        appointmentService.createAppointment(request);
        return "redirect:/api/home";
    }

    private static void commonFunc(Authentication auth, Model model) {
        if (auth != null) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            model.addAttribute("username", auth.getName());
            String roleLength = userDetails.getAuthorities().toString();
            model.addAttribute("role", roleLength.substring(1, roleLength.length() - 1));
        }
    }

}
