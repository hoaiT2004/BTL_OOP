package com.example.btl_oop.service.Impl;

import com.example.btl_oop.entity.Appointment;
import com.example.btl_oop.model.request.AppointmentRequest;
import com.example.btl_oop.model.response.AppointmentResponse;
import com.example.btl_oop.model.response.DeleteScheduleResponse;
import com.example.btl_oop.model.dto.AppointmentDto;
import com.example.btl_oop.repository.AppointmentRepository;
import com.example.btl_oop.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public AppointmentResponse createAppointment(AppointmentRequest request) {
        var appointment = Appointment.builder()
                .username(request.getUsername())
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

    @Override
    public List<AppointmentDto> getAllByUsername(String username, Pageable pageable) {
        Page<Appointment> pages = appointmentRepository.getAllByUsernameOrderByComeDateAsc(username, pageable);
        List<Appointment> appointmentList = new ArrayList<>();
        pages.forEach(appointmentList::add);
        return AppointmentDto.toDto(appointmentList);
    }

    @Override
    public DeleteScheduleResponse deleteScheduleById(Long scheduleId) {
        appointmentRepository.deleteById(scheduleId);
        return new DeleteScheduleResponse(scheduleId);
    }
}
