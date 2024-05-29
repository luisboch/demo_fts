package com.luis.demo.repository.write;

import com.luis.demo.model.entities.Cep;
import com.luis.demo.util.Repository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import static com.luis.demo.constants.Repository.*;

@Component
public class CepWriteRepository {

    private final NamedParameterJdbcTemplate template;

    public CepWriteRepository(
            @Qualifier("main")
            NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    private static final String UPSERT =
            "                 INSERT " +
                    "           INTO cep (zip_code, address, details, neighborhood, city_name, state_code, " +
                    "                 ibge_code, ddd) \n" +
                    "         VALUES (:" + ZIP_CODE + ", :" + ADDRESS + ", :" + DETAILS + ", :" + NEIGHBORHOOD + "," +
                    "                 :" + CITY_NAME + ", :" + STATE_CODE + ", :" + IBGE_CODE + ", :" + DDD + ")" +
                    "     ON CONFLICT (zip_code) " +
                    "       DO UPDATE " +
                    "             SET address       = :" + ADDRESS + "," +
                    "                 details       = :" + DETAILS + "," +
                    "                 neighborhood  = :" + NEIGHBORHOOD + "," +
                    "                 city_name     = :" + CITY_NAME + "," +
                    "                 state_code    = :" + STATE_CODE + "," +
                    "                 ibge_code     = :" + IBGE_CODE + "," +
                    "                 ddd           = :" + DDD + ";";

    public void saveOrUpdate(Cep entity) {

        final var parameters = new Repository.Parameters();
        parameters.put(ZIP_CODE, entity.zipCode());
        parameters.put(ADDRESS, entity.address());
        parameters.put(DETAILS, entity.details());
        parameters.put(NEIGHBORHOOD, entity.neighborhood());
        parameters.put(CITY_NAME, entity.cityName());
        parameters.put(STATE_CODE, entity.stateCode());
        parameters.put(IBGE_CODE, entity.ibgeCode());
        parameters.put(DDD, entity.ddd());

        template.update(
                UPSERT,
                parameters
        );
    }
}
