package com.example.btl_oop.repository;


import com.example.btl_oop.entity.Image;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("SELECT I.url FROM Image AS I" +
            " WHERE I.room_id = :room_id")
    List<String> findAllImagesByRoom_id(@Param("room_id") long room_id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Image i WHERE i.room_id = :roomId")
    void deleteAllImagesByRoomId(@Param("roomId") Long roomId);
}
