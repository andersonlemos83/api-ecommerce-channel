package br.com.alc.ecommerce.channel.infrastructure.cucumber.feature;

import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.finder.OrderFinderRequestDataTable;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.function.Function;

@Component
@AllArgsConstructor
public class FindOrdersByPeriodFeature {

    private static final String BASE_URL = "http://localhost:8383";
    private static final String URI = "/order/paginated";
    private static final int TEN_MB = 10 * 1024 * 1024;

    private final WebTestClient webTestClient;

    public WebTestClient.ResponseSpec execute(OrderFinderRequestDataTable orderFinderRequestDataTable) {
        return webTestClient.mutate()
                .baseUrl(BASE_URL)
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(TEN_MB))
                .build()
                .get()
                .uri(buildUriFunction(orderFinderRequestDataTable))
                .exchange();
    }

    private Function<UriBuilder, URI> buildUriFunction(OrderFinderRequestDataTable orderFinderRequestDataTable) {
        return uriBuilder -> uriBuilder.path(URI)
                .queryParam("startPeriodDate", orderFinderRequestDataTable.getStartPeriodDate())
                .queryParam("endPeriodDate", orderFinderRequestDataTable.getEndPeriodDate())
                .queryParam("pageNumber", orderFinderRequestDataTable.getPageNumber())
                .queryParam("pageSize", orderFinderRequestDataTable.getPageSize())
                .build();
    }
}