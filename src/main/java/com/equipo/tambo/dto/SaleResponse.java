package com.equipo.tambo.dto;

import com.equipo.tambo.entity.Client;
import com.equipo.tambo.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record SaleResponse (
        Long id,
        Client client,
        User user,
        LocalDateTime date,
        BigDecimal total,
        List<SaleDetailResponse> details
) {}