package br.com.alc.ecommerce.channel.infrastructure.web.controller;

import br.com.alc.ecommerce.channel.core.exception.OrderNotFoundException;
import br.com.alc.ecommerce.channel.core.exception.PeriodInvalidException;
import br.com.alc.ecommerce.channel.infrastructure.adapter.input.ByOrderNumberOrderFinderInAdapter;
import br.com.alc.ecommerce.channel.infrastructure.adapter.input.ByPeriodOrderFinderInAdapter;
import br.com.alc.ecommerce.channel.infrastructure.adapter.input.OrderNumberGeneratorInAdapter;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.error.ErrorResponseDataTable;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.verifier.ErrorResponseVerifier;
import br.com.alc.ecommerce.channel.infrastructure.dto.bot.OrderBotRequestDto;
import br.com.alc.ecommerce.channel.infrastructure.dto.finder.OrderFinderRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static java.time.LocalTime.MAX;
import static java.time.LocalTime.MIN;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@SuppressWarnings("java:S5786") // Public required for JUnit test suite
@WebFluxTest({OrderController.class, ErrorResponseVerifier.class})
public class OrderControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ErrorResponseVerifier errorResponseVerifier;

    @MockitoBean
    private OrderNumberGeneratorInAdapter orderNumberGeneratorInAdapterMock;

    @MockitoBean
    private ByOrderNumberOrderFinderInAdapter byOrderNumberOrderFinderInAdapterMock;

    @MockitoBean
    private ByPeriodOrderFinderInAdapter byPeriodOrderFinderInAdapterMock;

    @Test
    void givenAnInvalidOrderBotRequestWhenExecutingTheStartOrderBotRequestThenShouldThrowsAhWebExchangeBindException() {
        OrderBotRequestDto orderBotRequestDto = OrderBotRequestDto.builder().orderQuantity(null).build();
        ErrorResponseDataTable expected = ErrorResponseDataTable.builder().httpStatus(BAD_REQUEST).message("O campo orderQuantity não foi informado.").build();

        WebTestClient.ResponseSpec responseSpec = webTestClient.post()
                .uri("/order/start-bot")
                .bodyValue(orderBotRequestDto)
                .exchange();

        errorResponseVerifier.verify(Arrays.asList(expected), responseSpec);
    }

    @Test
    void givenAnInvalidOrderNumberWhenExecutingTheFindOrdersByOrderNumberRequestThenShouldThrowsAhOrderNotFoundException() {
        String orderNumber = "12345";
        ErrorResponseDataTable expected = ErrorResponseDataTable.builder().httpStatus(BAD_REQUEST).message("O pedido não foi encontrado.").build();

        when(byOrderNumberOrderFinderInAdapterMock.execute(orderNumber)).thenReturn(Mono.error(OrderNotFoundException::new));

        WebTestClient.ResponseSpec responseSpec = webTestClient.get()
                .uri("/order/" + orderNumber)
                .exchange();

        errorResponseVerifier.verify(Arrays.asList(expected), responseSpec);
    }

    @Test
    void givenAnInvalidOrderFinderRequestWhenExecutingTheFindOrdersByPeriodRequestThenShouldThrowsAhPeriodInvalidException() {
        OrderFinderRequestDto orderFinderRequestDto = buildOrderFinderRequestDto();
        ErrorResponseDataTable expected = ErrorResponseDataTable.builder().httpStatus(BAD_REQUEST).message("O período de 30/01/2025 00:00:00 até 29/01/2025 23:59:59 é inválido.").build();

        when(byPeriodOrderFinderInAdapterMock.execute(orderFinderRequestDto)).thenReturn(Flux.error(buildPeriodInvalidException(orderFinderRequestDto)));

        WebTestClient.ResponseSpec responseSpec = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/order/paginated")
                        .queryParam("startPeriodDate", orderFinderRequestDto.getStartPeriodDate())
                        .queryParam("endPeriodDate", orderFinderRequestDto.getEndPeriodDate())
                        .queryParam("pageNumber", orderFinderRequestDto.getPageNumber())
                        .queryParam("pageSize", orderFinderRequestDto.getPageSize())
                        .build())
                .exchange();

        errorResponseVerifier.verify(Arrays.asList(expected), responseSpec);
    }

    private OrderFinderRequestDto buildOrderFinderRequestDto() {
        return OrderFinderRequestDto.builder()
                .startPeriodDate(LocalDate.now()
                        .withYear(2025)
                        .withMonth(01)
                        .withDayOfMonth(30))
                .endPeriodDate(LocalDate.now()
                        .withYear(2025)
                        .withMonth(01)
                        .withDayOfMonth(29))
                .pageNumber(0)
                .pageSize(20)
                .build();
    }

    private PeriodInvalidException buildPeriodInvalidException(OrderFinderRequestDto orderFinderRequestDto) {
        LocalDateTime start = LocalDateTime.of(orderFinderRequestDto.getStartPeriodDate(), MIN);
        LocalDateTime end = LocalDateTime.of(orderFinderRequestDto.getEndPeriodDate(), MAX);
        return new PeriodInvalidException(start, end);
    }
}