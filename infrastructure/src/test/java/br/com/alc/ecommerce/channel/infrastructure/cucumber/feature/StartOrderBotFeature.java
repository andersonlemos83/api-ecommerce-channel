package br.com.alc.ecommerce.channel.infrastructure.cucumber.feature;

import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.bot.OrderBotRequestDataTable;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.feature.factory.WebTestClientFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.WebTestClient;

@Component
@AllArgsConstructor
public class StartOrderBotFeature {

    private static final String URI = "/order/start-bot";

    private final WebTestClientFactory webTestClientFactory;

    public WebTestClient.ResponseSpec execute(OrderBotRequestDataTable orderBotRequestDataTable) {
        return webTestClientFactory.getWebTestClient()
                .post()
                .uri(URI)
                .bodyValue(orderBotRequestDataTable)
                .exchange();
    }
}