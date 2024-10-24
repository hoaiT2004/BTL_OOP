package com.example.btl_oop.service;

import com.example.btl_oop.config.MyUserDetails;
import com.example.btl_oop.entity.User;
import com.example.btl_oop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optional = userRepository.findUserByUsername(username);
        if(!optional.isPresent()) throw new RuntimeException("Could not find user by username");
        User user = optional.get();
        return new MyUserDetails(user);
    }
}
