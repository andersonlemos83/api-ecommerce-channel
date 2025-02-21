package br.com.alc.ecommerce.channel.infrastructure.adapter.output;

import br.com.alc.ecommerce.channel.core.domain.order.OrderRequest;
import br.com.alc.ecommerce.channel.core.domain.order.OrderResponse;
import br.com.alc.ecommerce.channel.core.exception.DefaultOutPortException;
import br.com.alc.ecommerce.channel.core.port.output.OrderInvoicerOutPort;
import br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil;
import br.com.alc.ecommerce.channel.infrastructure.client.EcommerceCheckoutClient;
import br.com.alc.ecommerce.channel.infrastructure.dto.order.OrderRequestDto;
import br.com.alc.ecommerce.channel.infrastructure.dto.order.OrderResponseDto;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

import static br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil.generateJson;
import static org.apache.commons.lang3.exception.ExceptionUtils.getMessage;

@Log4j2
@Component
@AllArgsConstructor
public class OrderInvoicerOutPortImpl implements OrderInvoicerOutPort {

    private final EcommerceCheckoutClient ecommerceCheckoutClient;
    private final ModelMapper modelMapper;

    @Override
    @CircuitBreaker(name = "order-invoicer-circuitbreaker")
    public OrderResponse execute(OrderRequest orderRequest) {
        try {
            log.debug("Incoming into OrderInvoicerOutPortImpl: {}", generateJson(orderRequest));
            OrderRequestDto orderRequestDto = modelMapper.map(orderRequest, OrderRequestDto.class);
            log.info("---> Request /authorize-sale: {}", generateJson(orderRequestDto));
            OrderResponseDto orderResponseDto = ecommerceCheckoutClient.authorizeSale(orderRequestDto);
            log.info("<--- Response /authorize-sale: {}", generateJson(orderResponseDto));
            OrderResponse orderResponse = modelMapper.map(orderResponseDto, OrderResponse.class);
            log.debug("Outgoing from OrderInvoicerOutPortImpl: {}", generateJson(orderResponse));
            return orderResponse;
        } catch (FeignException exception) {
            log.error("Error in the TaxFinderOutPortImpl: {}", getMessage(exception), exception);
            Map<String, Object> properties = ObjectMapperUtil.generateMap(exception.contentUTF8());
            String message = Optional.ofNullable(properties.get("message"))
                    .map(String.class::cast)
                    .orElse(exception.contentUTF8());
            throw new DefaultOutPortException(message, exception.getCause());
        }
    }
}