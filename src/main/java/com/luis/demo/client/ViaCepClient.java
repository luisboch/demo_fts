package com.luis.demo.client;

import com.luis.demo.model.rest.inbound.ViaCepResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Log4j2
@Component
public class ViaCepClient {

    private final String viaCepUrl;
    private final RestClient restClient = RestClient.create();

    public ViaCepClient(@Value("${host.viacep}") String viaCepUrl) {
        this.viaCepUrl = viaCepUrl;
    }

    public ViaCepResponse getInfo(String cep) {
        log.info("getInfo");
        return restClient.get()
                .uri(viaCepUrl + "/ws/" + cep + "/json/")
                .retrieve()
                .body(ViaCepResponse.class);
    }
}
