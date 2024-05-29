package com.luis.demo.model.entities;


public record Cep(
        Long zipCode,
        String address,
        String details,
        String neighborhood,
        String cityName,
        String stateCode,
        Integer ibgeCode,
        Short ddd
) {
}
