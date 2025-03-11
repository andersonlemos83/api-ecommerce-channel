package br.com.alc.ecommerce.channel.infrastructure.client;

import br.com.alc.ecommerce.channel.infrastructure.client.fallback.EcommerceCheckoutClientFallbackFactory;
import br.com.alc.ecommerce.channel.infrastructure.dto.order.OrderRequestDto;
import br.com.alc.ecommerce.channel.infrastructure.dto.order.OrderResponseDto;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SuppressWarnings("squid:S7091") // Circular dependencies between classes across packages
@FeignClient(name = "api-ecommerce-checkout",
        url = "${client.ecommerce-checkout.url}",
        fallbackFactory = EcommerceCheckoutClientFallbackFactory.class)
public interface EcommerceCheckoutClient {

    @Headers("Content-Type: " + APPLICATION_JSON_VALUE)
    @PostMapping(value = "/authorize-sale", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    OrderResponseDto authorizeSale(OrderRequestDto orderRequestDto);

}