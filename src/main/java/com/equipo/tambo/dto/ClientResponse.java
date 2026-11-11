package com.equipo.tambo.dto;

public record ClientResponse (
        Long id,
        String name,
        String lastname,
        String email,
        String address,
        String dni
) {}
