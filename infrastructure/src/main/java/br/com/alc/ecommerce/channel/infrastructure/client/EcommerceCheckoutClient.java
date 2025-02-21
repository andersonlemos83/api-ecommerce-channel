package br.com.alc.ecommerce.channel.infrastructure.client;

import br.com.alc.ecommerce.channel.infrastructure.dto.order.OrderRequestDto;
import br.com.alc.ecommerce.channel.infrastructure.dto.order.OrderResponseDto;
import feign.Headers;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import static br.com.alc.ecommerce.channel.core.domain.order.SaleStatus.ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = "api-ecommerce-checkout", url = "${client.ecommerce-checkout.url}")
public interface EcommerceCheckoutClient {

    @Headers("Content-Type: " + APPLICATION_JSON_VALUE)
    @CircuitBreaker(name = "order-invoicer-circuitbreaker", fallbackMethod = "fallback")
    @PostMapping(value = "/authorize-sale", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    OrderResponseDto authorizeSale(OrderRequestDto orderRequestDto);

    default OrderResponseDto fallback(OrderRequestDto orderRequestDto, Throwable throwable) {
        return OrderResponseDto.builder()
                .status(ERROR)
                .build();
    }
}