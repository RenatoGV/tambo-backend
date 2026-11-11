package com.equipo.tambo.service;

import com.equipo.tambo.dto.UpdateUserRequest;
import com.equipo.tambo.dto.UserRequest;
import com.equipo.tambo.dto.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse register(UserRequest request);
    List<UserResponse> listUsers();
    UserResponse getById(Long id);
    UserResponse updateUser(Long id, UpdateUserRequest request);
    UserResponse changeUserStatus(Long id, boolean active);
    void deleteUser(Long id);
}
