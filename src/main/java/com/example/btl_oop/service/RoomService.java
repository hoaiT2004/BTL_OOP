package com.example.btl_oop.service;

import com.example.btl_oop.model.request.room.RoomFilterDataRequest;
import com.example.btl_oop.model.dto.RoomDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoomService {

    List<RoomDto> getAllRoomByManyContraints(RoomFilterDataRequest request, Pageable pageable);

    RoomDto getInfoRoomByRoom_Id(String room_id);

    List<String> getAllImagesByRoom_Id(String room_id);

}
