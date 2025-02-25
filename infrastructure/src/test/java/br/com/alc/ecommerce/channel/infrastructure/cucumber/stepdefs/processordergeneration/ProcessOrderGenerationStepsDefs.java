package br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.processordergeneration;

import br.com.alc.ecommerce.channel.core.service.generator.stub.OrderGeneratorServiceStub;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.generator.OrderGeneratorRequestDataTable;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.order.OrderRequestDataTable;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.feature.ProcessOrderGenerationFeature;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.StepDefs;
import io.cucumber.java.pt.Quando;
import lombok.AllArgsConstructor;

import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@SuppressWarnings("squid:S2925") // "Thread.sleep" should not be used in tests
public class ProcessOrderGenerationStepsDefs extends StepDefs {

    private final ProcessOrderGenerationFeature processOrderGenerationFeature;

    @Quando("^processar geracao pedido$")
    public void processarGeracaoPedido() throws Exception {
        createOrderRequest();

        OrderGeneratorRequestDataTable orderGeneratorRequestDataTable = transitionDataTable.getOrderGeneratorRequestDataTable();
        processOrderGenerationFeature.execute(orderGeneratorRequestDataTable);
        TimeUnit.MILLISECONDS.sleep(2500);
    }

    private void createOrderRequest() {
        OrderRequestDataTable orderRequestDataTable = transitionDataTable.buildOrderRequestDataTable();
        OrderGeneratorServiceStub.createOrderRequest(orderRequestDataTable);
    }
}