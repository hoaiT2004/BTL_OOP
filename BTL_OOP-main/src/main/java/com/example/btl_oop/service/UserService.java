package com.example.btl_oop.service;

import com.example.btl_oop.entity.User;
import com.example.btl_oop.model.request.user.ChangeAvatarRequest;
import com.example.btl_oop.model.request.user.ChangeInfoRequest;
import com.example.btl_oop.model.request.user.RegisterRequest;
import com.example.btl_oop.model.response.user.ChangeAvatarResponse;
import com.example.btl_oop.model.response.user.ChangeInfoResponse;
import com.example.btl_oop.model.response.user.RegisterResponse;
import com.example.btl_oop.model.response.user.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;

public interface UserService {

    RegisterResponse register(RegisterRequest request);

    ChangeInfoResponse changeInfo(ChangeInfoRequest request);

    ChangeAvatarResponse changeAvatar(ChangeAvatarRequest request);

    UserDto getUserById(long id);

    List<UserDto> getAllUser(String textSearch, Pageable pageable);

    UserDto findUserByUsername(String username);
}
