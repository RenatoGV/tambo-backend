package com.equipo.tambo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        String name,

        @NotBlank(message = "El apellido es obligatorio")
        @Size(max = 100, message = "El apellido no puede superar los 100 caracteres")
        String lastname,

        @NotBlank(message = "El usuario es obligatorio")
        @Size(max = 20, message = "El usuario no puede superar los 20 caracteres")
        String user,

        @NotNull(message = "El rol de usuario es obligatorio")
        Long role
) {}
