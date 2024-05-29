package com.luis.demo.controller;

import com.luis.demo.business.aggregate.CarAggregate;
import com.luis.demo.business.projection.CarProjection;
import com.luis.demo.model.command.CreateCarCommand;
import com.luis.demo.model.command.UpdateCarCommand;
import com.luis.demo.model.entities.Car;
import com.luis.demo.model.query.GetCarQuery;
import com.luis.demo.model.query.ListCarsQuery;
import com.luis.demo.model.rest.CarRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;

import static org.springframework.http.ResponseEntity.status;

@RestController
@Tag(name = "CarController")
@RequestMapping("/v1/car")
@AllArgsConstructor
public class CarController {

    private final CarProjection projection;
    private final CarAggregate aggregate;

    @GetMapping
    public Page<Car> getCars(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false) String brandId
    ) {
        return projection.handle(new ListCarsQuery(brandId, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCar(
            @PathVariable(name = "id") Long id
    ) {
        return projection.handle(new GetCarQuery(id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Car save(
            @Valid @RequestBody CarRequest request
    ) {
        return aggregate.handle(new CreateCarCommand(request));
    }

    @PutMapping("/{id}")
    public Car save(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody CarRequest request
    ) {
        return aggregate.handle(new UpdateCarCommand(id, request));
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleConflict(
            DataIntegrityViolationException exception,
            WebRequest request
    ) {

        val data = new HashMap<String, String>();
        data.put("message", "Data integrity violation");

        return status(HttpStatus.CONFLICT).body(data);
    }
}
