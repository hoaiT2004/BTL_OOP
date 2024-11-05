package com.example.btl_oop.service;

import com.example.btl_oop.model.request.user.*;
import com.example.btl_oop.model.response.user.*;
import org.springframework.data.domain.Pageable;

import java.util.*;

public interface UserService {

    RegisterResponse register(RegisterRequest request);

    ChangeInfoResponse changeInfo(ChangeInfoRequest request);

    ChangeAvatarResponse changeAvatar(ChangeAvatarRequest request);

    UserDto getUserById(long id);

    List<UserDto> getAllUser(String textSearch, Pageable pageable);

    UserDto findUserByUsername(String username);

    ChangePasswordResponse changePassword(ChangePasswordRequest request, String username);

    CreateNewPasswordResponse createNewPassword(CreateNewPasswordRequest request);
}
