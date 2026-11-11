package com.equipo.tambo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SaleRequest (
        Long client,

        @NotNull(message = "El ID del usuario/cajero es obligatorio")
        Long user,

        @Valid
        @NotNull(message = "Los detalles de la venta son obligatorias")
        List<SaleDetailRequest> details
) {}