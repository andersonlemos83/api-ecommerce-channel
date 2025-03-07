package br.com.alc.ecommerce.channel.infrastructure.client.stub;

import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.externalservices.JsonResponseDataTable;
import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static wiremock.org.eclipse.jetty.http.HttpStatus.BAD_REQUEST_400;

@Component
@RequiredArgsConstructor
public class EcommerceCheckoutClientStub {

    private static final String URL_AUTHORIZE_SALE = "/authorize-sale";

    private final WireMockServer wireMockServer;

    public void configureAuthorizeSaleEndpoint(JsonResponseDataTable jsonResponseDataTable) {
        if (jsonResponseDataTable.isStatusOk()) {
            wireMockServer.stubFor(post(urlEqualTo(URL_AUTHORIZE_SALE))
                    .withName(URL_AUTHORIZE_SALE)
                    .willReturn(okJson(jsonResponseDataTable.getResponse())));
        }
        if (jsonResponseDataTable.isStatusBadRequest()) {
            wireMockServer.stubFor(post(urlEqualTo(URL_AUTHORIZE_SALE))
                    .withName(URL_AUTHORIZE_SALE)
                    .willReturn(aResponse().withStatus(BAD_REQUEST_400).withBody(jsonResponseDataTable.getResponse())));
        }
    }
}