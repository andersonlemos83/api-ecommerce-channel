package br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.callback;

import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.callback.OrderCallbackRequestDataTable;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.StepDefs;
import io.cucumber.java.pt.E;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class OrderCallbackRequestStepDefs extends StepDefs {

    @E("^que seja informado os dados de Order Callback Request$")
    public void queSejaInformadoOsDadosDeOrderCallbackRequest(List<OrderCallbackRequestDataTable> orderCallbackRequestDataTableList) {
        orderCallbackRequestDataTableList.forEach(transitionDataTable::setOrderCallbackRequestDataTable);
    }
}