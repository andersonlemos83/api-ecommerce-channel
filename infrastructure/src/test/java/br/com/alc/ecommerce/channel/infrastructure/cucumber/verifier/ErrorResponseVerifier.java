package br.com.alc.ecommerce.channel.infrastructure.cucumber.verifier;

import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.error.ErrorResponseDataTable;
import br.com.alc.ecommerce.channel.infrastructure.dto.error.ErrorResponseDto;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Component
public class ErrorResponseVerifier {

    public void verify(List<ErrorResponseDataTable> expecteds, WebTestClient.ResponseSpec responseSpec) {
        expecteds.forEach(e -> verify(e, responseSpec));
    }

    @SneakyThrows
    private void verify(ErrorResponseDataTable expected, WebTestClient.ResponseSpec responseSpec) {
        ErrorResponseDto response = responseSpec.expectBody(ErrorResponseDto.class)
                .returnResult()
                .getResponseBody();

        assertEquals(expected.getHttpStatus(), response.getHttpStatus());
        assertEquals(expected.getMessage(), response.getMessage());
    }
}