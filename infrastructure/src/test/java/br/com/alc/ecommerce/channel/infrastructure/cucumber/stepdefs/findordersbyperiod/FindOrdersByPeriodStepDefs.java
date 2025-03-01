package br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.findordersbyperiod;

import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.finder.OrderFinderRequestDataTable;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.feature.FindOrdersByPeriodFeature;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.StepDefs;
import io.cucumber.java.pt.Quando;
import lombok.AllArgsConstructor;
import org.springframework.test.web.reactive.server.WebTestClient;

@AllArgsConstructor
public class FindOrdersByPeriodStepDefs extends StepDefs {

    private final FindOrdersByPeriodFeature findOrdersByPeriodFeature;

    @Quando("^consultar pedidos por periodo$")
    public void consultarPedidosPorPeriodo() {
        OrderFinderRequestDataTable orderFinderRequestDataTable = transitionDataTable.getOrderFinderRequestDataTable();
        WebTestClient.ResponseSpec response = findOrdersByPeriodFeature.execute(orderFinderRequestDataTable);
        transitionDataTable.setResponse(response);
    }
}