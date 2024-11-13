package com.example.btl_oop.repository;


import com.example.btl_oop.entity.Appointment;
import com.example.btl_oop.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}