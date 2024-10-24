package com.example.btl_oop.repository;

import com.example.btl_oop.entity.Booking;
import com.example.btl_oop.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
