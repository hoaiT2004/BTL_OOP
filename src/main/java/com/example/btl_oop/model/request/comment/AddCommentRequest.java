package com.example.btl_oop.model.request.comment;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddCommentRequest {

    private String username;

    private String content;

    private String commentTime;

    private String room_id;
}
