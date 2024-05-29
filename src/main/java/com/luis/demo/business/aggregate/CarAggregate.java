package com.luis.demo.business.aggregate;

import com.luis.demo.model.command.CreateCarCommand;
import com.luis.demo.model.command.UpdateCarCommand;
import com.luis.demo.model.entities.Car;
import com.luis.demo.repository.write.CarWriteRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
@Log4j2
public class CarAggregate {
    private final CarWriteRepository repository;

    @Transactional
    public Car handle(CreateCarCommand command) {
        return repository.save(
                new Car(
                        null,
                        command.name(),
                        command.brandId()
                )
        );
    }

    @Transactional
    @CacheEvict(cacheNames = "carCache", key = "#command.id")
    public Car handle(UpdateCarCommand command) {
        return repository.update(
                new Car(
                        command.id(),
                        command.name(),
                        command.brandId()
                )
        );
    }
}
