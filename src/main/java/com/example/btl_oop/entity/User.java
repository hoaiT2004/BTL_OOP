package com.example.btl_oop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class  User extends BaseEntity {

    @Column(unique = true)
    @Size(max = 100)
    @NotNull
    private String username;

    @Size(max = 100)
    @NotNull
    private String password;

    @Size(max = 100)
    @NotNull
    private String fullname;
    private String tel;
    private String email;
    //@OneToOne(cascade = CascadeType.ALL)
    //Mối quan hệ 1:1 với role
    private long role_id;
    private String linkAvatar;
}
