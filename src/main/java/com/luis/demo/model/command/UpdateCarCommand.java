package com.luis.demo.model.command;

import com.luis.demo.model.rest.CarRequest;

public record UpdateCarCommand(
        Long id,
        String name,
        Long brandId
) {

    public UpdateCarCommand(Long id, CarRequest carCommand) {
        this(id, carCommand.name(), carCommand.brandId());
    }
}
