package com.example.btl_oop.entity;


import com.example.btl_oop.common.RoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long role_id;

    @Enumerated(EnumType.STRING)
    private RoleEnum role_name;
}
