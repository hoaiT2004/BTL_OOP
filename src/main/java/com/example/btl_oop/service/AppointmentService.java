package com.example.btl_oop.service;

import com.example.btl_oop.model.request.AppointmentRequest;
import com.example.btl_oop.model.response.AppointmentResponse;
import com.example.btl_oop.model.dto.AppointmentDto;
import com.example.btl_oop.model.response.DeleteScheduleResponse;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.List;

public interface AppointmentService {

    AppointmentResponse createAppointment (AppointmentRequest request);

    List<AppointmentDto> getAllByUsername(String username, Pageable pageable) throws ParseException;

    DeleteScheduleResponse deleteScheduleById (Long scheduleId);
}
