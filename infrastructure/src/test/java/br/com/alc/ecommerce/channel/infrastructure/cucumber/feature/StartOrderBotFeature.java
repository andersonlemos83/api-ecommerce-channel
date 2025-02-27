package br.com.alc.ecommerce.channel.infrastructure.cucumber.feature;

import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.bot.OrderBotRequestDataTable;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.WebTestClient;

@Component
@AllArgsConstructor
public class StartOrderBotFeature {

    private static final String BASE_URL = "http://localhost:8383";
    private static final String URI = "/order/start-bot";
    private static final int TEN_MB = 10 * 1024 * 1024;

    private final WebTestClient webTestClient;

    public WebTestClient.ResponseSpec execute(OrderBotRequestDataTable orderBotRequestDataTable) {
        return webTestClient.mutate()
                .baseUrl(BASE_URL)
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(TEN_MB))
                .build()
                .post()
                .uri(URI)
                .bodyValue(orderBotRequestDataTable)
                .exchange();
    }
}