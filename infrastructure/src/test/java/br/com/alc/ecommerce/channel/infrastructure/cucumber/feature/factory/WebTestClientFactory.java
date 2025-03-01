package br.com.alc.ecommerce.channel.infrastructure.cucumber.feature.factory;

import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.WebTestClient;

@Component
public class WebTestClientFactory {

    private static final String BASE_URL = "http://localhost:8383";
    private static final int TEN_MB = 10 * 1024 * 1024;

    private final WebTestClient webTestClient;

    public WebTestClientFactory(WebTestClient webTestClient) {
        this.webTestClient = webTestClient.mutate()
                .baseUrl(BASE_URL)
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(TEN_MB))
                .build();
    }

    public WebTestClient getWebTestClient() {
        return webTestClient;
    }
}