package com.example.btl_oop.service;

import com.example.btl_oop.entity.Appointment;
import com.example.btl_oop.model.request.AppointmentRequest;
import com.example.btl_oop.model.request.schedule.UpdateScheduleRequest;
import com.example.btl_oop.model.response.AppointmentResponse;
import com.example.btl_oop.model.dto.AppointmentDto;
import com.example.btl_oop.model.response.DeleteScheduleResponse;
import com.example.btl_oop.model.response.schedule.UpdateScheduleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.text.ParseException;
import java.util.List;

public interface AppointmentService {

    AppointmentResponse createAppointment (AppointmentRequest request);

    Page<Appointment> getAllByUsername(String username, Pageable pageable) throws ParseException;

    DeleteScheduleResponse deleteScheduleById (Long scheduleId);

    Page<Appointment> getAppointmentsByUsername (String isApproval, String username, Pageable pageable);

    void permitAppointment(long appointmentId);

    UpdateScheduleResponse updateAppointment (UpdateScheduleRequest request);
}
