package com.example.btl_oop.service.Impl;

import com.example.btl_oop.config.MyUserDetails;
import com.example.btl_oop.entity.Role;
import com.example.btl_oop.entity.User;
import com.example.btl_oop.model.request.user.*;
import com.example.btl_oop.model.response.user.*;
import com.example.btl_oop.repository.RoleRepository;
import com.example.btl_oop.repository.UserRepository;
import com.example.btl_oop.service.FileService;
import com.example.btl_oop.service.UserService;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private FileService fileService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new RuntimeException("Could not find user by username"));
        Role role = roleRepository.findById(user.getRole_id()).orElse(null);
        return new MyUserDetails(user, role);
    }

    @Override
    public UserDto findUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new RuntimeException("Could not find user by username"));
        return UserDto.toDto(user);
    }

    @Override
    public ChangePasswordResponse changePassword(ChangePasswordRequest request, String username) {
        UserDetails userDetails = loadUserByUsername(username);
        if (request.getPassword().equals(userDetails.getPassword())) {
            throw new InvalidParameterException("Wrong password");
        }
        userRepository.updatePassword(passwordEncoder.encode(request.getNewPassword()), username);
        return new ChangePasswordResponse(username);
    }

    @Override
    public CreateNewPasswordResponse createNewPassword(CreateNewPasswordRequest request) {
        userRepository.updatePassword(passwordEncoder.encode(request.getNewPassword()), request.getUsername());
        return new CreateNewPasswordResponse(request.getUsername());
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest registerRequest) {
        if (userRepository.findUserByUsername(registerRequest.getUsername()).isPresent()) {
            throw new EntityExistsException("Account already exists!");
        }

        var user = User.builder()
                .username(registerRequest.getUsername().toLowerCase())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .fullname(registerRequest.getFullname())
                .email(registerRequest.getEmail())
                .tel(registerRequest.getTel())
                .role_id(Long.parseLong(registerRequest.getRole_id()))
                .linkAvatar("https://res.cloudinary.com/hoaptit/image/upload/v1714322737/samples/people/bicycle.jpg")
                .build();
        userRepository.save(user);
        return new RegisterResponse(user.getId());
    }

    @Override
    @Transactional
    public ChangeInfoResponse changeInfo(ChangeInfoRequest request) {
        userRepository.updateInfoUser(request.getTel(), request.getFullname(), request.getUsername());
        return new ChangeInfoResponse(request.getUsername());
    }

    @Override
    public ChangeAvatarResponse changeAvatar(ChangeAvatarRequest request) {
        String linkAvatar = fileService.uploadFile(request.getFile());
        userRepository.updateAvatarUser(linkAvatar, request.getUsername());
        return new ChangeAvatarResponse(request.getUsername());
    }

    @Override
    public UserDto getUserById(long id) {
        return null;
    }

    @Override
    public List<UserDto> getAllUser(String textSearch, Pageable pageable) {
        Page<User> userPage;
        if (textSearch != null) {
            userPage = userRepository.findByUsernameContaining(textSearch, pageable);
        } else {
            userPage = userRepository.findAll(pageable);
        }
        List<User> userList = new LinkedList<>();
        userPage.forEach(userList::add);
        return UserDto.toDto(userList);
    }
}
