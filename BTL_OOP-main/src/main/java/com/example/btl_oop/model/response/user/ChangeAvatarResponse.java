package com.example.btl_oop.model.response.user;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class ChangeAvatarResponse {
    private String username;
}

