package br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs;

import br.com.alc.ecommerce.channel.infrastructure.cucumber.verifier.OrderBotResponseVerifier;
import io.cucumber.java.pt.E;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderBotResponseStepDefs extends StepDefs {

    private final OrderBotResponseVerifier orderBotResponseVerifier;

    @E("^deveria receber \"([^\"]*)\" Order Bot Response$")
    public void deveriaReceberNOrderBotResponse(long quantity) {
        orderBotResponseVerifier.verify(quantity, transitionDataTable.getResponse());
    }
}