package br.com.alc.ecommerce.channel.infrastructure.cucumber.verifier;

import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.externalservices.JsonRequestDataTable;
import br.com.alc.ecommerce.channel.infrastructure.helper.util.ObjectMapperHelper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;

@Component
@AllArgsConstructor
public class EcommerceCheckoutClientVerifier {

    private static final String URL_AUTHORIZE_SALE = "/authorize-sale";

    private final WireMockServer wireMockServer;

    public void verifyAuthorizeSaleEnpoint(List<JsonRequestDataTable> expecteds) {
        List<LoggedRequest> requests = wireMockServer.findAll(postRequestedFor(urlEqualTo(URL_AUTHORIZE_SALE)));

        String expected = expecteds.stream()
                .map(JsonRequestDataTable::getRequest)
                .toList()
                .toString();

        String request = requests.stream()
                .map(LoggedRequest::getBodyAsString)
                .map(ObjectMapperHelper::generateOrderRequestDto)
                .map(ObjectMapperHelper::generateJson)
                .toList()
                .toString();

        assertEquals(expected, request);
    }
}