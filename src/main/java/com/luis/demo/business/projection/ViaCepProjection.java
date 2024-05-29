package com.luis.demo.business.projection;

import com.luis.demo.client.ViaCepClient;
import com.luis.demo.mapper.CepMapper;
import com.luis.demo.model.query.CepQuery;
import com.luis.demo.model.rest.inbound.ViaCepResponse;
import com.luis.demo.repository.read.CepReadRepository;
import com.luis.demo.repository.write.CepWriteRepository;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.stereotype.Component;

import static com.luis.demo.util.NumberUtil.toLong;

@AllArgsConstructor
@Log4j2
@Component
public class ViaCepProjection {
    private ViaCepClient client;
    private CepWriteRepository writeRepository;
    private CepReadRepository cepReadRepository;
    private CepMapper cepMapper;

    @Retry(name = "viaCepRetry", fallbackMethod = "getInfoFallback")
    public ViaCepResponse getInfo(CepQuery query) {
        val info = client.getInfo(query.cep());
        writeRepository.saveOrUpdate(cepMapper.toEntity(info));
        return info;
    }

    @SuppressWarnings("unused")
    public ViaCepResponse getInfoFallback(CepQuery query, Exception ex) {
        log.error(ex.getMessage(), ex);
        return cepReadRepository
                .getCep(toLong(query.cep()))
                .map(cep -> cepMapper.fromEntity(cep))
                .orElseThrow(() -> new RuntimeException(ex));
    }
}
