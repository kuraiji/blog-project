package com.kuraiji.blog.services;

import com.kuraiji.blog.domain.dto.UserDto;
import com.kuraiji.blog.domain.request.CreateUserRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDto createUser(CreateUserRequest request);
    List<UserDto> findAll();
    Optional<UserDto> findOne(Long id);
    boolean notExists(Long id);
    UserDto fullUpdateUser(CreateUserRequest request, Long id);
    UserDto partialUpdateUser(CreateUserRequest request, Long id);
    void delete(Long id);
}
