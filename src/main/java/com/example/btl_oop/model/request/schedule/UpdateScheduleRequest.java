package com.example.btl_oop.model.request.schedule;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateScheduleRequest {
    private String appointmentId, comeDate;
}