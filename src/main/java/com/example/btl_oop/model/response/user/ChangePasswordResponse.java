package com.example.btl_oop.model.response.user;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class ChangePasswordResponse {
    private String username;
}

