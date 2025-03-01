package br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.processordercallback;

import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.callback.OrderCallbackRequestDataTable;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.feature.ProcessOrderCallbackFeature;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.StepDefs;
import io.cucumber.java.pt.Quando;
import lombok.AllArgsConstructor;

import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@SuppressWarnings("squid:S2925") // "Thread.sleep" should not be used in tests
public class ProcessOrderCallbackStepsDefs extends StepDefs {

    private final ProcessOrderCallbackFeature processOrderCallbackFeature;

    @Quando("^processar callback pedido$")
    public void processarCallbackPedido() throws Exception {
        OrderCallbackRequestDataTable orderCallbackRequestDataTable = transitionDataTable.getOrderCallbackRequestDataTable();
        processOrderCallbackFeature.execute(orderCallbackRequestDataTable);
        TimeUnit.MILLISECONDS.sleep(2500);
    }
}