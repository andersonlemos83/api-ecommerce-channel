package br.com.alc.ecommerce.channel.infrastructure.cucumber.feature;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.text.MessageFormat;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@AllArgsConstructor
public class FindOrderByOrderNumberFeature {

    private static final String BASE_URL = "http://localhost:8181";
    private static final String URI_PATTERN = "/order/{0}";
    private static final int TEN_MB = 10 * 1024 * 1024;

    private final WebTestClient webTestClient;

    public WebTestClient.ResponseSpec execute(String orderNumber) {
        String uri = MessageFormat.format(URI_PATTERN, orderNumber);
        return this.webTestClient.mutate()
                .baseUrl(BASE_URL)
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(TEN_MB))
                .build()
                .get()
                .uri(uri)
                .accept(APPLICATION_JSON)
                .exchange();
    }
}