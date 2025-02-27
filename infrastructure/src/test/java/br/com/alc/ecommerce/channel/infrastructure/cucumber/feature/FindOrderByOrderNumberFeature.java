package br.com.alc.ecommerce.channel.infrastructure.cucumber.feature;

import br.com.alc.ecommerce.channel.infrastructure.cucumber.feature.factory.WebTestClientFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.text.MessageFormat;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@AllArgsConstructor
public class FindOrderByOrderNumberFeature {

    private static final String URI_PATTERN = "/order/{0}";

    private final WebTestClientFactory webTestClientFactory;

    public WebTestClient.ResponseSpec execute(String orderNumber) {
        String uri = MessageFormat.format(URI_PATTERN, orderNumber);
        return webTestClientFactory.getWebTestClient()
                .get()
                .uri(uri)
                .accept(APPLICATION_JSON)
                .exchange();
    }
}