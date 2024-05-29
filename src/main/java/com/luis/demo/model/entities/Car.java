package com.luis.demo.model.entities;

import jakarta.validation.constraints.*;

import java.io.Serializable;

public record Car(
        Long id,
        String name,
        Long brandId
) implements Serializable {
}
