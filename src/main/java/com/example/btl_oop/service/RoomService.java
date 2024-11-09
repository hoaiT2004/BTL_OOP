package com.example.btl_oop.service;

import com.example.btl_oop.entity.Room;
import com.example.btl_oop.model.request.room.RoomFilterDataRequest;
import com.example.btl_oop.model.response.room.RoomDto;
import com.example.btl_oop.model.response.user.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoomService {

    List<RoomDto> getAllRoomByManyContraints(RoomFilterDataRequest request, Pageable pageable);


}
