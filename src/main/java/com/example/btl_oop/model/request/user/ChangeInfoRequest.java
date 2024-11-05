package com.example.btl_oop.model.request.user;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class ChangeInfoRequest {
    private String username, fullname, tel;
}

