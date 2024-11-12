package com.example.btl_oop.model.response.room;

import com.example.btl_oop.common.RoomType;
import com.example.btl_oop.entity.Room;
import com.example.btl_oop.entity.User;
import com.example.btl_oop.model.response.user.UserDto;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoomDto {

    private long room_id;

    private long user_id;

    private String address;

    private long capacity;

    private double price;

    private String description;

    private String roomType;

    private double area;

    private String isApproval;

    private String image;

    public static RoomDto toDto(Room room) {
        var roomDto = RoomDto.builder()
                .room_id(room.getId())
                .user_id(room.getUser_id())
                .address(room.getAddress())
                .capacity(room.getCapacity())
                .price(room.getPrice())
                .description(room.getDescription())
                .roomType(room.getRoomType().name())
                .area(room.getArea())
                .isApproval(room.getIsApproval())
                .image(room.getImage())
                .build();
        if (room.getRoomType().name().equals("CHUNG_CHU")) {
            roomDto.setRoomType("Chung chủ");
        } else {
            roomDto.setRoomType("Không chung chủ");
        }
        return roomDto;
    }

    public static Room toRoom(RoomDto room) {
        return Room.builder()
                .user_id(room.user_id)
                .address(room.address)
                .capacity(room.capacity)
                .price(room.price)
                .description(room.description)
                .roomType(RoomType.valueOf(room.roomType))
                .area(room.area)
                .isApproval(room.isApproval)
                .image(room.image)
                .build();
    }

    public static List<RoomDto> toDto(List<Room> rooms) {
        return rooms.stream()
                .map(RoomDto::toDto)
                .collect(Collectors.toList());
    }

    public static List<Room> toRoom(List<RoomDto> roomDtos) {
        return roomDtos.stream()
                .map(RoomDto::toRoom)
                .collect(Collectors.toList());
    }
}
