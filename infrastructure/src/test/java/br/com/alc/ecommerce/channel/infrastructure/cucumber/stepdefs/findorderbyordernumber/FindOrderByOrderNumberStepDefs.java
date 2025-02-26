package br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.findorderbyordernumber;

import br.com.alc.ecommerce.channel.infrastructure.cucumber.feature.FindOrderByOrderNumberFeature;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.StepDefs;
import io.cucumber.java.pt.Quando;
import lombok.AllArgsConstructor;
import org.springframework.test.web.reactive.server.WebTestClient;

@AllArgsConstructor
public class FindOrderByOrderNumberStepDefs extends StepDefs {

    private final FindOrderByOrderNumberFeature findOrderByOrderNumberFeature;

    @Quando("^consultar pedido por order number \"([^\"]*)\"$")
    public void consultarPedidoPorOrderNumber(String orderNumber) {
        WebTestClient.ResponseSpec response = findOrderByOrderNumberFeature.execute(orderNumber);
        transitionDataTable.setResponse(response);
    }
}