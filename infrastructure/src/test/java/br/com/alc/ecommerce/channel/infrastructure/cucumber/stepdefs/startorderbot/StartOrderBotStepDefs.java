package br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.startorderbot;

import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.bot.OrderBotRequestDataTable;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.feature.StartOrderBotFeature;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.StepDefs;
import io.cucumber.java.pt.Quando;
import lombok.AllArgsConstructor;
import org.springframework.test.web.reactive.server.WebTestClient;

@AllArgsConstructor
public class StartOrderBotStepDefs extends StepDefs {

    private final StartOrderBotFeature startOrderBotFeature;

    @Quando("^iniciar bot de pedidos$")
    public void iniciarBotDePedidos() {
        OrderBotRequestDataTable orderBotRequestDataTable = transitionDataTable.getOrderBotRequestDataTable();
        WebTestClient.ResponseSpec response = startOrderBotFeature.execute(orderBotRequestDataTable);
        transitionDataTable.setResponse(response);
    }
}