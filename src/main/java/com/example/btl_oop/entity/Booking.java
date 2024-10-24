package com.example.btl_oop.entity;

import com.example.btl_oop.common.BookingStatusEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import jakarta.websocket.OnError;
import lombok.*;

import java.util.Date;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Booking extends BaseEntity {

    //@OneToOne
    //Mối quan hệ 1:1 với user
    private long room_id;

    //@OneToOne
    //Mối quan hệ 1:1 với user
    private long user_id;

    private Date booking_date;

    private Date check_in_date;

    private Date check_out_date;

    @Enumerated(EnumType.STRING)
    private BookingStatusEnum status;
}
