package com.equipo.tambo.dto;

import com.equipo.tambo.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private String lastname;
    private String user;
    private Role role;
    private boolean active;
}
