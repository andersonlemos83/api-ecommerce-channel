package br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.externalservices;

import br.com.alc.ecommerce.channel.infrastructure.client.stub.EcommerceCheckoutClientStub;
import br.com.alc.ecommerce.channel.infrastructure.client.stub.ViaCepClientStub;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.externalservices.JsonRequestDataTable;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.externalservices.JsonResponseDataTable;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.StepDefs;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.verifier.EcommerceCheckoutClientVerifier;
import io.cucumber.java.pt.E;
import lombok.AllArgsConstructor;

import java.util.List;

import static java.util.Collections.emptyList;

@AllArgsConstructor
public class ExternalServicesStepDefs extends StepDefs {

    private final ViaCepClientStub viaCepClientStub;
    private final EcommerceCheckoutClientStub ecommerceCheckoutClientStub;

    private final EcommerceCheckoutClientVerifier ecommerceCheckoutClientVerifier;

    @E("^que existam os seguintes responses disponiveis no endpoint findByZipCode$")
    public void queExistamOsSeguintesResponsesDisponiveisNoEndpointFindByZipCode(List<JsonResponseDataTable> jsonResponseDataTableList) {
        jsonResponseDataTableList.forEach(viaCepClientStub::configureFindByZipCodeEndpoint);
    }

    @E("^que existam os seguintes responses disponiveis no endpoint authorize-sale$")
    public void queExistamOsSeguintesResponsesDisponiveisNoEndpointAuthorizeSale(List<JsonResponseDataTable> jsonResponseDataTableList) {
        jsonResponseDataTableList.forEach(ecommerceCheckoutClientStub::configureAuthorizeSaleEndpoint);
    }

    @E("^deveria enviar para o endpoint authorize-sale os requests esperados$")
    public void deveriaEnviarParaOhEndpointAuthorizeSaleOsRequestsEsperados(List<JsonRequestDataTable> jsonRequestDataTableList) {
        ecommerceCheckoutClientVerifier.verifyAuthorizeSaleEnpoint(jsonRequestDataTableList);
    }

    @E("^nao deveria enviar nenhum request para o endpoint authorize-sale")
    public void naoDeveriaEnviarNenhumRequestParaOhendpointAuthorizeSale() {
        ecommerceCheckoutClientVerifier.verifyAuthorizeSaleEnpoint(emptyList());
    }
}