package com.luis.demo.model.rest;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import static com.luis.demo.constants.Validation.INVALID_BRAND_ID;
import static com.luis.demo.constants.Validation.INVALID_NAME;

public record CarRequest(

        @NotNull(message = INVALID_NAME)
        @NotBlank(message = INVALID_NAME)
        @Size(max = 5, message = INVALID_NAME)
        String name,

        @Min(value = 1, message = INVALID_BRAND_ID)
        @NotNull(message = INVALID_BRAND_ID)
        Long brandId
) {
}
