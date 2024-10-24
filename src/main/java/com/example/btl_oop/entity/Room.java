package com.example.btl_oop.entity;

import com.example.btl_oop.common.RoomType;
import com.example.btl_oop.common.StatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Room extends BaseEntity {

    @OneToOne(cascade = CascadeType.ALL)
    private User user_id;

    @Size(max = 255)
    private String address;

    @Min(1)
    private long price;

    @Size(max = 255)
    private String description;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    private double area;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;
}
