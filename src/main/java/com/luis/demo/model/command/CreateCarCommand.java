package com.luis.demo.model.command;

import com.luis.demo.model.rest.CarRequest;

public record CreateCarCommand(
        String name,
        Long brandId
) {
    public CreateCarCommand(CarRequest request) {
        this(request.name(), request.brandId());
    }
}
