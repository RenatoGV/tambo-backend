package com.equipo.tambo.dto;

import com.equipo.tambo.entity.Category;

import java.math.BigDecimal;

public record ProductResponse (
        Long id,
        String name,
        String description,
        BigDecimal cost,
        Integer stock,
        Boolean active,
        Category category
) {}