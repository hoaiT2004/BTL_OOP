package com.example.btl_oop.service.Impl;

import com.example.btl_oop.model.request.AppointmentRequest;
import com.example.btl_oop.model.response.AppointmentResponse;

public interface AppointmentServiceImpl {

    AppointmentResponse createAppointment (AppointmentRequest request);
}
