package com.luis.demo.model.rest.inbound;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ViaCepResponse(
        @JsonProperty("cep")
        String zipCode,

        @JsonProperty("logradouro")
        String address,

        @JsonProperty("complemento")
        String details,

        @JsonProperty("bairro")
        String neighborhood,

        @JsonProperty("localidade")
        String cityName,

        @JsonProperty("uf")
        String stateCode,

        @JsonProperty("ibge")
        Integer ibgeCode,

        @JsonProperty("ddd")
        Short ddd
) {
}