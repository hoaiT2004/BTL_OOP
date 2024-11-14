package com.example.btl_oop.service.Impl;

import com.example.btl_oop.common.RoomType;
import com.example.btl_oop.common.RoomTypeConverter;
import com.example.btl_oop.entity.Image;
import com.example.btl_oop.entity.Room;
import com.example.btl_oop.entity.User;
import com.example.btl_oop.model.request.room.RoomFilterDataRequest;
import com.example.btl_oop.model.response.room.ImageDto;
import com.example.btl_oop.model.response.room.RoomDto;
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
    public void addRoom(RoomDto roomDto, List<MultipartFile> images, Authentication auth) {
        Room room = RoomDto.toRoom(roomDto);
        room.setIsApproval("false");
        if(auth != null) {
            String username = auth.getName();
            Optional<User> user =  userRepository.findUserByUsername(username);
            room.setUser_id(user.get().getId());
        }
        String url = fileService.uploadFile((MultipartFile) images.get(0));
        room.setImage(url);
        roomRepository.save(room);

        for(int i=1;i<images.size();i++) {
            Image image = new Image();
            image.setRoom_id(room.getId());
            String imageUrl = fileService.uploadFile(images.get(i));
            image.setUrl(imageUrl);
            imageRepository.save(image);
        }
    }

}
