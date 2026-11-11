package com.equipo.tambo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClientRequest (
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        String name,

        @NotBlank(message = "El apellido es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        String lastname,

        @Email(message = "Formato de correo inválido")
        String email,

        @Size(max = 200, message = "La dirección no puede superar los 200 caracteres")
        String address,

        @NotBlank(message = "El DNI es obligatorio")
        @Size(min = 8, max = 10, message = "El DNI debe tener entre 8 y 10 dígitos")
        String dni
) {}