package com.example.btl_oop.model.request.user;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String fullname;
    private String tel;
    private String role;
}

