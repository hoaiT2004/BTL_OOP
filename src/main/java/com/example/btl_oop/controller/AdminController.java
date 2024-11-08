package com.example.btl_oop.controller;

import com.example.btl_oop.model.request.room.RoomFilterDataRequest;
import com.example.btl_oop.model.response.room.RoomDto;
import com.example.btl_oop.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("api/home")
public class AdminController {

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

    @GetMapping("/UserManagement")
    public String userManagement() {
        // Logic cho trang UserManagement nếu cần thiết
        return "UserManagement";  // Đảm bảo "UserManagement" là tên view đúng
    }

    @GetMapping("/RoomManagement")
    public String roomManagement(Authentication authentication, Model model,
                                 @ModelAttribute RoomFilterDataRequest request,
                                 @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
        func_common(authentication, model);  // Lấy thông tin người dùng và role
        Pageable pageable = PageRequest.of(pageNo - 1, sizeOfPage); // Phân trang

        // Lấy danh sách phòng theo các bộ lọc và phân trang
        Page<RoomDto> roomList = roomService.getAllRoomByManyConstraints(request, pageable);

        model.addAttribute("rooms", roomList.getContent());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPage", roomList.getTotalPages());

        // Lưu trạng thái lọc khi chuyển trang
        model.addAttribute("request", request);

        return "RoomManagement"; // Đảm bảo "RoomManagement" là tên view đúng
    }
}
