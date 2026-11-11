package com.equipo.tambo.dto;

import com.equipo.tambo.entity.Role;

public record UserResponse (
        Long id,
        String name,
        String lastname,
        String user,
        Role role,
        boolean active
) {}
