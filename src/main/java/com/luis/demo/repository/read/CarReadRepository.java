package com.luis.demo.repository.read;

import com.luis.demo.model.entities.Car;
import com.luis.demo.model.query.GetCarQuery;
import com.luis.demo.model.query.ListCarsQuery;
import com.luis.demo.repository.mapper.CarDbMapper;
import com.luis.demo.util.Repository.PaginationParameters;
import com.luis.demo.util.Repository.Parameters;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.luis.demo.constants.Repository.*;

@Log4j2
@Component
public class CarReadRepository {

    private static final String GET_QUERY =
            "        SELECT id, name, brand_id " +
                    "  FROM car " +
                    " WHERE id = :" + ID;

    private static final String LIST_QUERY =
            "        SELECT id, name, brand_id " +
                    "  FROM car " +
                    " WHERE (brand_id = :" + BRAND_ID + " or COALESCE(:" + BRAND_ID + ", '<<EMPTY>>') = '<<EMPTY>>')" +
                    " LIMIT :" + LIMIT +
                    " OFFSET :" + OFFSET + " ";

    private static final String COUNT_QUERY =
            "        SELECT COUNT(id) " +
                    "  FROM car " +
                    " WHERE (brand_id = :" + BRAND_ID + " or COALESCE(:" + BRAND_ID + ", '<<EMPTY>>') = '<<EMPTY>>')";

    private final NamedParameterJdbcTemplate template;

    @Autowired
    public CarReadRepository(
            @Qualifier("replica") NamedParameterJdbcTemplate template
    ) {
        this.template = template;
    }

    public Page<Car> getCars(ListCarsQuery query) {

        final PaginationParameters holder = new PaginationParameters(query.page(), query.size());
        holder.put(BRAND_ID, query.brandId());

        List<Car> cars = template.query(
                LIST_QUERY,
                holder,
                new CarDbMapper()
        );

        Long quantity = template.queryForObject(COUNT_QUERY, holder, Long.class);

        assert quantity != null;
        log.info("getCars: found {} cars ", quantity);
        return new PageImpl<>(
                cars,
                PageRequest.of(query.page(), query.size()),
                quantity
        );
    }

    public Optional<Car> getCar(GetCarQuery query) {
        final Parameters holder = new Parameters();
        holder.put(ID, query.id());

        try {
            val car = template.queryForObject(GET_QUERY, holder,
                    new CarDbMapper()
            );
            log.info("getCar: Found car with id {}  ", query.id());
            return Optional.ofNullable(car);
        } catch (EmptyResultDataAccessException ex) {
            log.warn("getCar: not found car with id {}  ", query.id());
            return Optional.empty();
        }
    }
}
