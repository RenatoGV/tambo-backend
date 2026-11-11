package com.equipo.tambo.dto;

public record AuthResponse(
       String accessToken,
       String tokenType,
       long expiresIn
) {}
