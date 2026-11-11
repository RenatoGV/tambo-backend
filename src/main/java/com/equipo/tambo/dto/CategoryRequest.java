package com.equipo.tambo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest (
        @NotBlank(message = "El nombre de la categoría es obligatorio")
        @Size(max = 100, message = "La categoría no puede superar los 100 caracteres")
        String category
) {}
