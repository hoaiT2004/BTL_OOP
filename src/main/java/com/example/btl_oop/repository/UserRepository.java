package com.example.btl_oop.repository;

import com.example.btl_oop.entity.Booking;
import com.example.btl_oop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findUserByUsernameLike(String username);

    Optional<User> findUserByUsername(String username);
}
