package com.luis.demo.repository.read;

import com.luis.demo.model.entities.Cep;
import com.luis.demo.repository.mapper.CepDbMapper;
import com.luis.demo.util.Repository;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.luis.demo.constants.Repository.ZIP_CODE;

@Component
@Log4j2
public class CepReadRepository {

    private final NamedParameterJdbcTemplate template;

    private static final String GET_QUERY =
            "           SELECT zip_code, address, details, neighborhood, city_name, state_code, ibge_code, ddd\n" +
                    "     FROM cep " +
                    "    WHERE zip_code = :" + ZIP_CODE;

    public CepReadRepository(
            @Qualifier("replica") NamedParameterJdbcTemplate template
    ) {
        this.template = template;
    }

    public Optional<Cep> getCep(Long query) {
        final Repository.Parameters holder = new Repository.Parameters();
        holder.put(ZIP_CODE, query);


        try {
            val cep = template.queryForObject(GET_QUERY, holder,
                    new CepDbMapper()
            );
            log.info("getCep: Found cep {}", cep);
            return Optional.ofNullable(cep);
        } catch (EmptyResultDataAccessException ex) {
            log.warn("getCep: not found cep with {}", query);
            return Optional.empty();
        }
    }
}
