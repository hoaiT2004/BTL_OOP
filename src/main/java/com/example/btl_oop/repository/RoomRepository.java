package com.example.btl_oop.repository;


import com.example.btl_oop.entity.Room;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findRoomByIsApproval(String isApproval);
}
