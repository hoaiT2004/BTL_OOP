package com.example.btl_oop.service.Impl;

import com.example.btl_oop.common.RoomType;
import com.example.btl_oop.common.RoomTypeConverter;
import com.example.btl_oop.entity.Image;
import com.example.btl_oop.entity.Room;
import com.example.btl_oop.entity.User;
import com.example.btl_oop.model.request.room.RoomFilterDataRequest;
import com.example.btl_oop.model.dto.*;
import com.example.btl_oop.repository.ImageRepository;
import com.example.btl_oop.repository.RoomRepository;
import com.example.btl_oop.repository.UserRepository;
import com.example.btl_oop.service.FileService;
import com.example.btl_oop.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private ImageRepository imageRepository;

    private static final String isApproval = "true";
    private static final String isNotApproval = "false";
    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<Room> getAllRoomByManyContraints(RoomFilterDataRequest request, Pageable pageable) {
        Page<Room> roomPage;
        if (request.isNull()) {
            // Nếu không có bộ lọc, lấy tất cả các phòng đã duyệt
            roomPage = roomRepository.findAllByIsApproval(isApproval, pageable);
        } else {
            RoomType roomType;
            try {
                roomType = RoomTypeConverter.convertToEntityAttributeGlobal(request.getRoomType());
            } catch (Exception ex) {
                roomType = null;
            }

            // Thêm điều kiện lọc cho trạng thái duyệt trong trường hợp có bộ lọc
            roomPage = roomRepository.findAllByFilterConstraints(request.getPrice(), request.getAddress(), request.getArea(), roomType, pageable);
        }
        return roomPage;
    }


    @Override
    public RoomDto getInfoRoomByRoom_Id(String room_id) {
        return RoomDto.toDto(roomRepository.findById(Long.parseLong(room_id)).orElse(null));
    }

    @Override
    public List<String> getAllImagesByRoom_Id(String room_id) {
        return imageRepository.findAllImagesByRoom_id(Long.parseLong(room_id));
    }

    @Override
    @Transactional
    public void addRoom(RoomDto roomDto, List<MultipartFile> images, Authentication auth) {
        Room room = RoomDto.toRoom(roomDto);
        room.setIsApproval("false");
        if (auth != null) {
            String username = auth.getName();
            Optional<User> user = userRepository.findUserByUsername(username);
            room.setUser_id(user.get().getId());
        }


        String url = fileService.uploadFile((MultipartFile) images.get(0));
        room.setImage(url);
        roomRepository.save(room);
        for (int i = 0; i < images.size(); i++) {
            Image image = new Image();
            image.setRoom_id(room.getId());
            String imageUrl = fileService.uploadFile(images.get(i));
            image.setUrl(imageUrl);
            imageRepository.save(image);
        }
    }

    @Override
    public Page<Room> getAllRoomsForAdmin(RoomFilterDataRequest request, Pageable pageable) {
        Page<Room> roomPage;

        if (request.isNull()) {
            // Lấy tất cả các phòng (bao gồm cả đã duyệt và chưa duyệt)
            roomPage = roomRepository.findAll(pageable);
        } else {
            RoomType roomType;
            try {
                roomType = RoomTypeConverter.convertToEntityAttributeGlobal(request.getRoomType());
            } catch (Exception ex) {
                roomType = null;
            }
            roomPage = roomRepository.findAllByFilterConstraintsWithoutApproval(
                    request.getPrice(), request.getAddress(), request.getArea(), roomType, pageable);
        }
        return roomPage;
    }

    @Override
    @Transactional
    public void approveRoom(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));
        room.setIsApproval("true");
        roomRepository.save(room);
    }

    // Không duyệt phòng
    @Override
    @Transactional
    public void disapproveRoom(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));
        roomRepository.deleteById(roomId);
    }
}
