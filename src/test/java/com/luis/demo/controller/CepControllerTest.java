package com.luis.demo.controller;

import com.luis.demo.BaseIntegrationTests;
import com.luis.demo.model.entities.Cep;
import com.luis.demo.repository.write.CepWriteRepository;
import org.junit.jupiter.api.Test;
import org.mockserver.mock.Expectation;
import org.mockserver.verify.VerificationTimes;
import org.springframework.beans.factory.annotation.Autowired;

import static com.luis.demo.MockData.VIA_CEP_RESPONSE;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CepControllerTest extends BaseIntegrationTests {

    @Autowired
    CepWriteRepository cepWriteRepository;

    private Expectation enqueueServiceUnavailableResponse() {
        return mockServer.when(
                request()
                        .withMethod("GET")
                        .withPath("/ws/{cep}/json/")
                        .withPathParameter("cep")
        ).respond(
                response().withStatusCode(503)
        )[0];
    }

    private Expectation enqueueSuccessResponse() {
        return mockServer.when(
                request()
                        .withMethod("GET")
                        .withPath("/ws/{cep}/json/")
                        .withPathParameter("cep")
        ).respond(response().withStatusCode(200)
                .withBody(VIA_CEP_RESPONSE)
                .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        )[0];
    }

    @Test
    public void GIVEN_validCep_WHEN_callViaCepWithSuccess_THEN_returnsSuccess() throws Exception {

        Expectation expectation = enqueueSuccessResponse();

        mockMvc.perform(get("/v1/cep/1111111"))
                .andExpect(status().isOk());

        mockServer.verify(expectation.getId(),
                VerificationTimes.exactly(1)
        );
    }

    @Test
    public void GIVEN_validCep_WHEN_callViaCepWithErrorLocalUnavailable_THEN_returnsError() throws Exception {

        // GIVEN
        Expectation expectation = enqueueServiceUnavailableResponse();
        cepWriteRepository.saveOrUpdate(new Cep(
                1111112L,
                "Praça da Sé",
                "lado ímpar",
                "Sé",
                "São Paulo",
                "SP",
                3550308,
                (short) 11
        ));

        mockMvc.perform(get("/v1/cep/1111112"))
                .andExpect(status().isOk());

        mockServer.verify(expectation.getId(),
                VerificationTimes.exactly(2)
        );
    }

    @Test
    public void GIVEN_validCep_WHEN_callViaCepWithErrorLocalAvailable_THEN_returnsOk() throws Exception {

        Expectation expectation = enqueueServiceUnavailableResponse();

        mockMvc.perform(get("/v1/cep/1111113"))
                .andExpect(status().isInternalServerError());

        mockServer.verify(expectation.getId(),
                VerificationTimes.exactly(2)
        );
    }
}