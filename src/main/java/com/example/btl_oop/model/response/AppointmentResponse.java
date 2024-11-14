package com.example.btl_oop.model.response;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
public class AppointmentResponse {

    private long appointment_id;
}
