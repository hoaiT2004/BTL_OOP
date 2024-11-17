package com.example.btl_oop.service.Impl;

import com.example.btl_oop.common.RoomType;
import com.example.btl_oop.common.RoomTypeConverter;
import com.example.btl_oop.entity.Room;
import com.example.btl_oop.entity.User;
import com.example.btl_oop.model.request.room.RoomFilterDataRequest;
import com.example.btl_oop.model.dto.*;
import com.example.btl_oop.repository.ImageRepository;
import com.example.btl_oop.repository.RoomRepository;
import com.example.btl_oop.repository.UserRepository;
import com.example.btl_oop.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ImageRepository imageRepository;

    private static final String isApproval = "true";

    @Override
    public List<RoomDto> getAllRoomByUser(String username){
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
        roomRepository.deleteById(room_id);
    }

    @Override
    public List<RoomDto> getAllRoomByManyContraints(RoomFilterDataRequest request, Pageable pageable) {
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
        List<Room> roomList = new LinkedList<>();
        roomPage.forEach(roomList::add);
        return RoomDto.toDto(roomList);
    }

    @Override
    public RoomDto getInfoRoomByRoom_Id(String room_id) {
        return RoomDto.toDto(roomRepository.findById(Long.parseLong(room_id)).orElse(null));
    }

    @Override
    public List<String> getAllImagesByRoom_Id(String room_id) {
        return imageRepository.findAllImagesByRoom_id(Long.parseLong(room_id));
    }

}
