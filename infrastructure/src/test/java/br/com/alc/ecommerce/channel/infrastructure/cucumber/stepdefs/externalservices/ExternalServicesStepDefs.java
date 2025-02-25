package br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.externalservices;

import br.com.alc.ecommerce.channel.infrastructure.client.stub.ViaCepClientStub;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.externalservices.JsonResponseDataTable;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.StepDefs;
import io.cucumber.java.pt.E;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ExternalServicesStepDefs extends StepDefs {

    private final ViaCepClientStub viaCepClientStub;

    @E("^que existam os seguintes responses disponiveis no endpoint findByCep$")
    public void queExistamOsSeguintesResponsesDisponiveisNoEndpointFindByCep(List<JsonResponseDataTable> jsonResponseDataTableList) {
        jsonResponseDataTableList.forEach(viaCepClientStub::configureFindByCep$Endpoint);
    }
}