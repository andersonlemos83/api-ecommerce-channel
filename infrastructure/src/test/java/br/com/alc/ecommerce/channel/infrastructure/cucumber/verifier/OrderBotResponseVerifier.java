package br.com.alc.ecommerce.channel.infrastructure.cucumber.verifier;

import br.com.alc.ecommerce.channel.infrastructure.dto.bot.OrderBotResponseDto;
import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

@Component

public class OrderBotResponseVerifier {

    public void verify(long quantityExpected, WebTestClient.ResponseSpec responseSpec) {
        responseSpec.expectStatus()
                .isCreated()
                .expectHeader()
                .contentType(TEXT_EVENT_STREAM_VALUE);

        long quantityResponse = responseSpec.expectBodyList(OrderBotResponseDto.class)
                .returnResult()
                .getResponseBody()
                .stream()
                .count();

        assertEquals(quantityExpected, quantityResponse, "Should return the expected number of Order Bot Response.");
    }
}