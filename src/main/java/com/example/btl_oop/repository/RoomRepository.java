package com.example.btl_oop.repository;


import com.example.btl_oop.common.RoomType;
import com.example.btl_oop.entity.Room;
import java.util.*;

import com.example.btl_oop.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    Page<Room> findAllByIsApproval(String isApproval, Pageable pageable);

//    @Modifying -- Có cái này để DB biết file này để update|delete (Kh có mặc định là đọc)
    @Query("SELECT r FROM Room AS r WHERE " +
            "(r.isApproval = 'true')" +
            "    AND (:price = '' OR " +
            "        (:price = '1' AND r.price < 1) OR" +
            "        (:price = '1-2' AND r.price >= 1 AND r.price <= 2) OR" +
            "        (:price = '2-3' AND r.price >= 2 AND r.price <= 3) OR" +
            "        (:price = '3' AND r.price > 3)" +
            "    ) " +
            "    AND (:address = '' OR r.address LIKE CONCAT('%', :address, '%'))" +
            "    AND (:area = '' OR " +
            "        (:area = '20' AND r.area < 20) OR" +
            "        (:area = '20-30' AND r.area >= 20 AND r.area <= 30) OR" +
            "        (:area = '30-40' AND r.area >= 30 AND r.area <= 40) OR" +
            "        (:area = '40' AND r.area > 40)" +
            "    ) " +
            "    AND (:roomType IS NULL OR r.roomType = :roomType)")
    Page<Room> findAllByFilterConstraints(@Param("price") String price, @Param("address") String address, @Param("area") String area, @Param("roomType") RoomType roomType, Pageable pageable);

    @Query("SELECT r FROM Room AS r WHERE " +
            "(r.isApproval = 'true')" +
            "    AND (:price = '' OR " +
            "        (:price = '1' AND r.price < 1) OR" +
            "        (:price = '1-2' AND r.price >= 1 AND r.price <= 2) OR" +
            "        (:price = '2-3' AND r.price >= 2 AND r.price <= 3) OR" +
            "        (:price = '3' AND r.price > 3)" +
            "    ) " +
            "    AND (:address = '' OR r.address LIKE CONCAT('%', :address, '%'))" +
            "    AND (:area = '' OR " +
            "        (:area = '20' AND r.area < 20) OR" +
            "        (:area = '20-30' AND r.area >= 20 AND r.area <= 30) OR" +
            "        (:area = '30-40' AND r.area >= 30 AND r.area <= 40) OR" +
            "        (:area = '40' AND r.area > 40)" +
            "    ) " +
            "    AND (:roomType IS NULL OR (:roomType IS NOT NULL AND r.roomType = :roomType))")
    List<Room> findAllByFilterConstraintsWithoutPagination(@Param("price") String price, @Param("address") String address, @Param("area") String area, @Param("roomType") RoomType roomType);
}
