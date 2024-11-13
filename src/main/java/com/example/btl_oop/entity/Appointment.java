package com.example.btl_oop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String fullname;

    @Column(nullable = false, length = 15)
    private String tel;

    @Column(nullable = false, length = 50)
    private String email;

    @Min(1)
    private int numPeople;

    private Date comeDate;

    @Column(nullable = false, length = 50)
    private String transportation;

    private long room_id;
}