package br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.orderfinderresponse;

import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.finder.OrderFinderResponseDataTable;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.StepDefs;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.verifier.OrderFinderResponseVerifier;
import io.cucumber.java.pt.Entao;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class OrderFinderResponseStepDefs extends StepDefs {

    private final OrderFinderResponseVerifier orderFinderResponseVerifier;

    @Entao("^deveria receber os dados de Full Order Finder Response$")
    public void deveriaReceberOsDadosDeFullOrderFinderResponse(List<OrderFinderResponseDataTable> orderFinderResponseDataTableList) {
        orderFinderResponseVerifier.verify(orderFinderResponseDataTableList, transitionDataTable.getResponse());
    }
}