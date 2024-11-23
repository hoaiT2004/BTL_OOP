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
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<RoomDto> getAllRoomByUser(String username) {
        Optional<User> user = userRepository.findUserByUsername(username);
        List<Room> rooms = roomRepository.findAllByUserid(user.get().getId());
        List<RoomDto> roomDtos = new ArrayList<>();
        for (Room room : rooms) {
            RoomDto roomDto = RoomDto.toDto(room);
            roomDtos.add(roomDto);
        }
        return roomDtos;
    }

    @Override
    public void deleteRoomByRoomId(Long room_id) {
        imageRepository.deleteAllImagesByRoomId(room_id);
        roomRepository.deleteById(room_id);
    }

    @Override
    public Page<Room> getRoomsByUser(String username, Pageable pageable) {
        Optional<User> user = userRepository.findUserByUsername(username);
        return roomRepository.getAllByUserId(user.get().getId(), pageable);
    }

    @Override
    public void updateRoom(RoomDto roomDto, Authentication auth, List<MultipartFile> imagesAdd, List<Long> imageIdsDel) {
        Room room = RoomDto.toRoom(roomDto);
        room.setIsApproval("false");
        Room oldroom = roomRepository.findById(roomDto.getRoom_id()).orElse(null);
        room.setId(roomDto.getRoom_id());
        room.setCreatedAt(oldroom.getCreatedAt());
        if (auth != null) {
            String username = auth.getName();
            Optional<User> user = userRepository.findUserByUsername(username);
            room.setUser_id(user.get().getId());
        }

        if (imageIdsDel != null ) {
            for (Long id : imageIdsDel) {
                Optional<Image> image = imageRepository.findById(id);
                if (room.getImage().equals(image.get().getUrl())) {
                    room.setImage("");
                }
            }
            imageRepository.deleteAllById(imageIdsDel);
        }

        if (imagesAdd.size() > 0) {
            for (int i = 0; i < imagesAdd.size(); i++) {//luu anh vao bang image
                Image image = new Image();
                image.setRoom_id(room.getId());
                String imageUrl = fileService.uploadFile(imagesAdd.get(i));
                image.setUrl(imageUrl);
                imageRepository.save(image);
            }
        }
        String s = room.getImage();
        if (room.getImage().equals(null) || room.getImage().equals("")) {
            List<String> images = imageRepository.findAllImagesByRoom_id(room.getId());
            room.setImage(images.get(0));//luu anh vao roomentity
        }
        System.out.println("ok");
        roomRepository.save(room);
    }


    @Override
    public Page<Room> getAllRoomByManyContraints(RoomFilterDataRequest request, Pageable pageable) {
        Page<Room> roomPage;
        if (request.isNull()) {
            roomPage = roomRepository.findAllByIsApproval(isApproval, pageable);
        } else {
            RoomType roomType;
            try {
                roomType = RoomTypeConverter.convertToEntityAttributeGlobal(request.getRoomType());
            } catch (Exception ex) {
                roomType = null;
            }
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

    @Modifying
    @Override
    @Transactional
    public void addRoom(RoomDto roomDto, List<MultipartFile> images, Authentication auth) {
        Room room = RoomDto.toRoom(roomDto);
        room.setIsApproval("false");
        if (roomDto.getRoom_id() != 0) {
            Room oldroom = roomRepository.findById(roomDto.getRoom_id()).orElse(null);
            room.setId(roomDto.getRoom_id());
            room.setCreatedAt(oldroom.getCreatedAt());
        }
        if (auth != null) {
            String username = auth.getName();
            Optional<User> user = userRepository.findUserByUsername(username);
            room.setUser_id(user.get().getId());
        }

        if (images.size() > 0) {
            room.setImage(fileService.uploadFile((MultipartFile) images.get(0)));//luu anh vao roomentity
            roomRepository.save(room);
            for (int i = 0; i < images.size(); i++) {//luu anh vao bang image
                Image image = new Image();
                image.setRoom_id(room.getId());
                String imageUrl = fileService.uploadFile(images.get(i));
                image.setUrl(imageUrl);
                imageRepository.save(image);
            }
        }
        roomRepository.save(room);
    }

}
