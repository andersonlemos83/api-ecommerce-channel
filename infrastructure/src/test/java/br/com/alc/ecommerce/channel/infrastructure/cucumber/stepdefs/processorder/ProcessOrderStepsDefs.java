package br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.processorder;

import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.order.OrderRequestDataTable;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.feature.ProcessOrderFeature;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.StepDefs;
import io.cucumber.java.pt.Quando;
import lombok.AllArgsConstructor;

import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@SuppressWarnings("squid:S2925") // "Thread.sleep" should not be used in tests
public class ProcessOrderStepsDefs extends StepDefs {

    private final ProcessOrderFeature processOrderFeature;

    @Quando("^processar pedido$")
    public void processarPedido() throws Exception {
        OrderRequestDataTable orderRequestDataTable = transitionDataTable.buildOrderRequestDataTable();
        processOrderFeature.execute(orderRequestDataTable);
        TimeUnit.MILLISECONDS.sleep(2500);
    }
}