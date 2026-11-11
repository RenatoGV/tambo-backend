package com.equipo.tambo.dto;

import com.equipo.tambo.entity.Product;

import java.math.BigDecimal;

public record SaleDetailResponse (
        Long id,
        Product product,
        Long quantity,
        BigDecimal subtotal
) {}