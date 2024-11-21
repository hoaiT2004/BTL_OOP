package com.example.btl_oop.repository;

import com.example.btl_oop.entity.Comment;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Configuration
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment AS c " +
            "WHERE c.room_id = :room_id " +
            "ORDER BY c.commentTime desc")
    List<Comment> getCommentsByRoom_id(@Param("room_id") long room_id);
}
