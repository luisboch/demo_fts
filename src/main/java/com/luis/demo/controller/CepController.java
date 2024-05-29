package com.luis.demo.controller;

import com.luis.demo.business.projection.ViaCepProjection;
import com.luis.demo.model.entities.Cep;
import com.luis.demo.model.query.CepQuery;
import com.luis.demo.model.rest.inbound.ViaCepResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "CepController")
@RestController
@RequestMapping("/v1/cep")
@AllArgsConstructor
public class CepController {

    private ViaCepProjection projection;

    @GetMapping("/{cep}")
    public ResponseEntity<ViaCepResponse> getCep(@PathVariable(name = "cep") String cep) {
        return ResponseEntity.ok(projection.getInfo(new CepQuery(cep)));
    }
}
