package com.example.btl_oop.repository;

import com.example.btl_oop.entity.Booking;
import com.example.btl_oop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);
}
