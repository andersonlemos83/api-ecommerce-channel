package br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.verifier;

import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.finder.HalfOrderFinderResponseDataTable;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.finder.OrderFinderResponseDataTable;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.StepDefs;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.verifier.OrderFinderResponseVerifier;
import io.cucumber.java.pt.E;
import lombok.AllArgsConstructor;

import java.util.List;

import static java.util.Collections.emptyList;

@AllArgsConstructor
public class OrderFinderResponseStepDefs extends StepDefs {

    private final OrderFinderResponseVerifier orderFinderResponseVerifier;

    @E("^deveria receber os dados de Full Order Finder Response$")
    public void deveriaReceberOsDadosDeFullOrderFinderResponse(List<OrderFinderResponseDataTable> orderFinderResponseDataTableList) {
        orderFinderResponseVerifier.verifyFullOrderFinderResponse(orderFinderResponseDataTableList, transitionDataTable.getResponse());
    }

    @E("^deveria receber os dados de Half Order Finder Response$")
    public void deveriaReceberOsDadosDeHalfOrderFinderResponse(List<HalfOrderFinderResponseDataTable> halfOrderFinderResponseDataTableList) {
        orderFinderResponseVerifier.verifyHalfOrderFinderResponse(halfOrderFinderResponseDataTableList, transitionDataTable.getResponse());
    }

    @E("^nao deveria receber nenhum Half Order Finder Response$")
    public void naoDeveriaReceberNenhumHalfOrderFinderResponse() {
        orderFinderResponseVerifier.verifyHalfOrderFinderResponse(emptyList(), transitionDataTable.getResponse());
    }

    @E("^deveria receber \"([^\"]*)\" Half Order Finder Response$")
    public void deveriaReceberNHalfOrderFinderResponse(long quantity) {
        orderFinderResponseVerifier.verifyHalfOrderFinderResponse(quantity, transitionDataTable.getResponse());
    }
}