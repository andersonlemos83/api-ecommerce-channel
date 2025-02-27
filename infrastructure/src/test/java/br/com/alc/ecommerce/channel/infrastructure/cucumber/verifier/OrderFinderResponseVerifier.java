package br.com.alc.ecommerce.channel.infrastructure.cucumber.verifier;

import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.finder.HalfOrderFinderResponseDataTable;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.finder.OrderFinderResponseDataTable;
import br.com.alc.ecommerce.channel.infrastructure.dto.finder.FullOrderFinderResponseDto;
import br.com.alc.ecommerce.channel.infrastructure.dto.finder.HalfOrderFinderResponseDto;
import br.com.alc.ecommerce.channel.infrastructure.helper.util.ObjectMapperHelper;
import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

@Component
public class OrderFinderResponseVerifier {

    public void verifyFullOrderFinderResponse(List<OrderFinderResponseDataTable> expecteds, WebTestClient.ResponseSpec responseSpec) {
        responseSpec.expectStatus()
                .isOk()
                .expectHeader()
                .contentType(APPLICATION_JSON_VALUE);

        expecteds.forEach(e -> verifyFullOrderFinderResponse(e, responseSpec));
    }

    public void verifyHalfOrderFinderResponse(List<HalfOrderFinderResponseDataTable> expecteds, WebTestClient.ResponseSpec responseSpec) {
        responseSpec.expectStatus()
                .isOk()
                .expectHeader()
                .contentType(TEXT_EVENT_STREAM_VALUE);

        List<HalfOrderFinderResponseDto> responses = responseSpec.expectBodyList(HalfOrderFinderResponseDto.class)
                .returnResult()
                .getResponseBody();

        assertEquals(expecteds.size(), responses.size(), "Should return the expected number of Half Order Finder Response.");

        for (int i = 0; i < expecteds.size(); i++) {
            HalfOrderFinderResponseDataTable expected = expecteds.get(i);
            HalfOrderFinderResponseDto response = responses.get(i);

            verify(expected, response);
        }
    }

    private void verifyFullOrderFinderResponse(OrderFinderResponseDataTable expected, WebTestClient.ResponseSpec responseSpec) {
        FullOrderFinderResponseDto fullOrderFinderResponseDto = responseSpec.expectBody(FullOrderFinderResponseDto.class)
                .returnResult()
                .getResponseBody();
        fullOrderFinderResponseDto.setId(null);
        String jsonResponse = ObjectMapperHelper.generateJson(fullOrderFinderResponseDto);

        assertEquals(expected.getJson(), jsonResponse);
    }

    private void verify(HalfOrderFinderResponseDataTable expected, HalfOrderFinderResponseDto response) {
        assertEquals(expected.getChannelCode(), response.getChannelCode());
        assertEquals(expected.getCompanyCode(), response.getCompanyCode());
        assertEquals(expected.getStoreCode(), response.getStoreCode());
        assertEquals(expected.getPos(), response.getPos());
        assertEquals(expected.getOrderNumber(), response.getOrderNumber());
        assertEquals(expected.getTotalValue(), response.getTotalValue());
        assertEquals(expected.getFreightValue(), response.getFreightValue());
        assertEquals(expected.getInvoiceKey(), response.getInvoiceKey());
        assertEquals(expected.getInvoiceNumber(), response.getInvoiceNumber());
        assertEquals(expected.getIssuanceDate(), response.getIssuanceDate());
        assertEquals(expected.getStatus(), response.getStatus());
        assertEquals(expected.getErrorReason(), response.getErrorReason());
        assertEquals(expected.getCreatedDate(), response.getCreatedDate());
        assertEquals(expected.getUpdatedDate(), response.getUpdatedDate());
    }
}