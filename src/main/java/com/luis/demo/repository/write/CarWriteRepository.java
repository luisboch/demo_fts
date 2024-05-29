package com.luis.demo.repository.write;

import com.luis.demo.model.entities.Car;
import com.luis.demo.util.Repository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static com.luis.demo.constants.Repository.*;

@Log4j2
@Component
public class CarWriteRepository {

    private final NamedParameterJdbcTemplate template;
    private static final String UPDATE =
            "        UPDATE car " +
                    "   SET      name = :" + NAME + "," +
                    "        brand_id = :" + BRAND_ID +
                    " WHERE id = :" + ID + ";";

    private static final String INSERT = "INSERT INTO car(name, brand_id) VALUES(:" + NAME + ", :" + BRAND_ID + ");";

    @Autowired
    public CarWriteRepository(
            @Qualifier("main") NamedParameterJdbcTemplate template
    ) {
        this.template = template;
    }

    public Car save(Car car) {
        final var parameters = new Repository.Parameters();
        parameters.put(NAME, car.name());
        parameters.put(BRAND_ID, car.brandId());

        template.update(
                INSERT,
                parameters
        );

        Long id = template.queryForObject("SELECT CURRVAL('public.car_id_seq')", new HashMap<>(), Long.class);
        log.info("save: id {}  ", id);
        return new Car(
                id,
                car.name(),
                car.brandId()
        );
    }

    public Car update(Car car) {

        final var parameters = new Repository.Parameters();
        parameters.put(NAME, car.name());
        parameters.put(BRAND_ID, car.brandId());
        parameters.put(ID, car.id());

        template.update(UPDATE, parameters);

        log.info("update car with id: {}", car.id());
        return car;
    }
}
