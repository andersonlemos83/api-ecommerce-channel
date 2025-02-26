package br.com.alc.ecommerce.channel.infrastructure.cucumber.verifier;

import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.finder.OrderFinderResponseDataTable;
import br.com.alc.ecommerce.channel.infrastructure.dto.finder.FullOrderFinderResponseDto;
import br.com.alc.ecommerce.channel.infrastructure.helper.util.ObjectMapperHelper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class OrderFinderResponseVerifier {

    public void verify(List<OrderFinderResponseDataTable> expecteds, WebTestClient.ResponseSpec responseSpec) {
        expecteds.forEach(e -> verify(e, responseSpec));
    }

    @SneakyThrows
    private void verify(OrderFinderResponseDataTable expected, WebTestClient.ResponseSpec responseSpec) {
        responseSpec.expectStatus()
                .isOk()
                .expectHeader()
                .contentType(APPLICATION_JSON_VALUE);

        assertEquals(expected.getJson(), generateResponse(responseSpec));
    }

    private String generateResponse(WebTestClient.ResponseSpec responseSpec) {
        FullOrderFinderResponseDto fullOrderFinderResponseDto = responseSpec.expectBody(FullOrderFinderResponseDto.class)
                .returnResult()
                .getResponseBody();
        fullOrderFinderResponseDto.setId(null);
        return ObjectMapperHelper.generateJson(fullOrderFinderResponseDto);
    }
}