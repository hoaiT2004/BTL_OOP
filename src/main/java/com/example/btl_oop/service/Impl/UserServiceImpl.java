package com.example.btl_oop.service.Impl;

import com.example.btl_oop.config.MyUserDetails;
import com.example.btl_oop.entity.User;
import com.example.btl_oop.model.request.user.RegisterRequest;
import com.example.btl_oop.model.response.user.RegisterResponse;
import com.example.btl_oop.model.response.user.UserDto;
import com.example.btl_oop.repository.UserRepository;
import com.example.btl_oop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optional = userRepository.findUserByUsername(username);
        if(!optional.isPresent()) throw new RuntimeException("Could not find user by username");
        User user = optional.get();
        return new MyUserDetails(user);
    }

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest registerRequest) {
        User user1 = userRepository.findUserByUsername(registerRequest.getUsername()).orElse(null);
        if(user1 != null) return null;

        var user = User.builder()
                .username(registerRequest.getUsername().toLowerCase())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .fullname(registerRequest.getFullname())
                .email(registerRequest.getEmail())
                .tel(registerRequest.getTel())
                .role_id(Long.parseLong(registerRequest.getRole()))
                .build();
        userRepository.save(user);
        return new RegisterResponse(user.getId());
    }

//    @Override
//    public EditResponse edit(EditRequest request) {
//        return null;
//    }

    @Override
    public UserDto getUserById(long id) {
        return null;
    }

    @Override
    public Page<UserDto> getAllUser(Integer pageNo) {
        return null;
    }

    @Override
    public Page<UserDto> searchCategory(String keyword, Integer pageNo) {
        return null;
    }

    @Override
    public List<UserDto> searchCategory(String keyword) {
        return UserDto.toDto(userRepository.findUserByUsernameLike("%" + keyword + "%"));
    }
}
