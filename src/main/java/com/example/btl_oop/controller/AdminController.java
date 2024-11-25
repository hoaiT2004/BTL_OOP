    package com.example.btl_oop.controller;

    import com.example.btl_oop.model.dto.UserDto;
    import com.example.btl_oop.model.request.room.RoomFilterDataRequest;
    import com.example.btl_oop.model.dto.RoomDto;
    import com.example.btl_oop.service.Impl.UserServiceImpl;
    import com.example.btl_oop.service.RoomService;
    import com.example.btl_oop.service.UserService;
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
    @RequestMapping("/api/home/admin")
    public class AdminController {
        @Autowired
        private RoomService roomService;
        @Autowired
        private UserService userService;

        private static final int sizeOfPage = 10;


        private void func_common(Authentication authentication, Model model) {
            if (authentication != null) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                model.addAttribute("username", userDetails.getUsername());
                String roleLength = userDetails.getAuthorities().toString();
                model.addAttribute("role", roleLength.substring(1, roleLength.length() - 1));
            }
        }
        @GetMapping("/RoomManagement")
        public String home(Authentication authentication, Model model,
                           @ModelAttribute RoomFilterDataRequest request
                , @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
            func_common(authentication, model);
            Pageable pageable = PageRequest.of(pageNo - 1, sizeOfPage);
            Page<Room> roomPage = roomService.getAllRoomsForAdmin( request, pageable);
            List<Room> roomList = new LinkedList<>();
            roomPage.forEach(roomList::add);
            model.addAttribute("rooms", RoomDto.toDto(roomList));
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("totalPage", roomPage.getTotalPages() == 0 ? 1 : roomPage.getTotalPages());

            // Lưu trạng thái lọc khi chuyển trang
            model.addAttribute("request", request);
            return "RoomManagement";
        }

        @GetMapping("/UserManagement")
        public String usermanagement(Authentication authentication, Model model,
                                     @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                     @RequestParam(name = "name", required = false) String name,
                                     @RequestParam(name = "tel", required = false) String tel) {
            func_common(authentication, model);

            if (pageNo < 1) {
                pageNo = 1; // Đảm bảo pageNo luôn bắt đầu từ 1
            }

            Pageable pageable = PageRequest.of(pageNo - 1, sizeOfPage);
            Page<UserDto> userPage = userService.getAllUserForAdmin(name, tel, pageable);
            List<UserDto> userList = new LinkedList<>();
            userPage.forEach(userList::add);
            model.addAttribute("users", userList);
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("totalPage", userPage.getTotalPages() == 0 ? 1 : userPage.getTotalPages());

            // Lưu trạng thái tìm kiếm khi chuyển trang
            model.addAttribute("name", name);
            model.addAttribute("tel", tel);

            return "UserManagement";
        }
        @PostMapping("/approveRoom")
        public String approveRoom(@RequestParam Long roomId) {
            roomService.approveRoom(roomId);
            return "redirect:/api/home/admin/RoomManagement";
        }

        @PostMapping("/disapproveRoom")
        public String disapproveRoom(@RequestParam Long roomId) {
            roomService.disapproveRoom(roomId);
            return "redirect:/api/home/admin/RoomManagement";
        }


    }