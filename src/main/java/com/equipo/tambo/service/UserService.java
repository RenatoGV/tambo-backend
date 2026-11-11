package com.equipo.tambo.service;

import com.equipo.tambo.dto.UserRequest;
import com.equipo.tambo.dto.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> listUsers();
    UserResponse getById(Long id);
    UserResponse updateUser(Long id, UserRequest request);
    UserResponse changeUserStatus(Long id, boolean active);
    void deleteUser(Long id);
}
