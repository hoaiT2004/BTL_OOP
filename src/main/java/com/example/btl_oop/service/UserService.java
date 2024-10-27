package com.example.btl_oop.service;

import com.example.btl_oop.model.request.user.RegisterRequest;
import com.example.btl_oop.model.response.user.RegisterResponse;
import com.example.btl_oop.model.response.user.UserDto;
import org.springframework.data.domain.Page;
import java.util.*;

public interface UserService {

    RegisterResponse register(RegisterRequest request);

   // EditResponse edit(EditRequest request);

    UserDto getUserById(long id);

    Page<UserDto> getAllUser(Integer pageNo);

    Page<UserDto> searchCategory(String keyword, Integer pageNo);

    List<UserDto> searchCategory(String keyword);
}
