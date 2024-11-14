package com.example.btl_oop.service;

import com.example.btl_oop.entity.Appointment;
import com.example.btl_oop.model.request.AppointmentRequest;
import com.example.btl_oop.model.response.AppointmentResponse;
import com.example.btl_oop.repository.AppointmentRepository;
import com.example.btl_oop.service.Impl.AppointmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;

@Service
public class AppointmentService implements AppointmentServiceImpl {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public AppointmentResponse createAppointment(AppointmentRequest request) {
        var appointment = Appointment.builder()
                .room_id(Long.parseLong(request.getRoom_id()))
                .fullname(request.getFullname())
                .email(request.getEmail())
                .tel(request.getTel())
                .numPeople(request.getNumPeople())
                .comeDate(Date.valueOf(request.getComeDate()))
                .transportation(request.getTransportation())
                .build();
        appointment = appointmentRepository.save(appointment);
        return new AppointmentResponse(appointment.getId());
    }
}
