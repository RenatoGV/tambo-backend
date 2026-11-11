package com.equipo.tambo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
      @NotBlank(message = "El nombre es obligatorio")
      @Size(max = 100)
      String name,

      @NotBlank(message = "El apellido es obligatorio")
      @Size(max = 100)
      String lastname,

      @NotBlank(message = "El usuario es obligatorio")
      @Size(max = 20)
      String user,

      @NotBlank(message = "La contraseña es obligatoria")
      @Size(min = 6, max = 72, message = "La contraseña debe tener entre 6 y 72 caracteres")
      String password
) {}
