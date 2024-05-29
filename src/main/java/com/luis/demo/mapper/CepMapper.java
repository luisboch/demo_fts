package com.luis.demo.mapper;

import com.luis.demo.model.entities.Cep;
import com.luis.demo.model.rest.inbound.ViaCepResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CepMapper {
    @Mappings(
            @Mapping(
                    target = "zipCode",
                    expression = "java(com.luis.demo.util.NumberUtil.toLong(response.zipCode()))"
            )
    )
    Cep toEntity(ViaCepResponse response);

    @Mappings(
            @Mapping(
                    target = "zipCode",
                    expression = "java(com.luis.demo.util.CepUtil.formatCep(cep.zipCode()))"
            )
    )
    ViaCepResponse fromEntity(Cep cep);
}
