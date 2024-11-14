package com.example.btl_oop.service.Impl;

import com.example.btl_oop.common.RoomType;
import com.example.btl_oop.common.RoomTypeConverter;
import com.example.btl_oop.entity.Image;
import com.example.btl_oop.entity.Room;
import com.example.btl_oop.model.request.room.RoomFilterDataRequest;
import com.example.btl_oop.model.response.room.ImageDto;
import com.example.btl_oop.model.response.room.RoomDto;
import com.example.btl_oop.repository.ImageRepository;
import com.example.btl_oop.repository.RoomRepository;
import com.example.btl_oop.service.FileService;
import com.example.btl_oop.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private ImageRepository imageRepository;

    private static final String isApproval = "true";

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

    @Override
    public void addRoom(RoomDto roomDto, List<MultipartFile> images) {
        Room room = RoomDto.toRoom(roomDto);
        room.setIsApproval("false");
        roomRepository.save(room);

        for(MultipartFile item : images) {
            Image image = new Image();
            image.setRoom_id(room.getId());
            String imageUrl = fileService.uploadFile(item);
            image.setUrl(imageUrl);
            imageRepository.save(image);
        }
    }

}
