package br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.bot;

import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.bot.OrderBotRequestDataTable;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.StepDefs;
import io.cucumber.java.pt.E;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class OrderBotRequestStepDefs extends StepDefs {

    @E("^que seja informado os dados de Order Bot Request$")
    public void queSejaInformadoOsDadosDeOrderBotRequest(List<OrderBotRequestDataTable> orderBotRequestDataTableList) {
        orderBotRequestDataTableList.forEach(transitionDataTable::setOrderBotRequestDataTable);
    }
}