package com.luis.demo.business.projection;

import com.luis.demo.model.entities.Car;
import com.luis.demo.model.query.GetCarQuery;
import com.luis.demo.model.query.ListCarsQuery;
import com.luis.demo.repository.read.CarReadRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@AllArgsConstructor
public class CarProjection {
    private final CarReadRepository readRepository;

    @Cacheable(cacheNames = "carCache", key = "#query.id")
    public Optional<Car> handle(GetCarQuery query) {
        return readRepository.getCar(query);
    }

    public Page<Car> handle(ListCarsQuery query) {
        return readRepository.getCars(query);
    }

}

