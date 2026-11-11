package com.equipo.tambo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SaleDetailRequest (
        @NotNull(message = "El producto es obligatorio")
        Long product,

        @NotNull(message = "La cantidad es obligatoria")
        @Positive(message = "La cantidad debe ser mayor que cero")
        Long quantity
) {}