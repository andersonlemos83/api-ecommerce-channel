package br.com.alc.ecommerce.channel.infrastructure.adapter.output;

import br.com.alc.ecommerce.channel.core.domain.order.OrderRequest;
import br.com.alc.ecommerce.channel.core.port.output.OrderInvoicerOutPort;
import br.com.alc.ecommerce.channel.infrastructure.client.EcommerceCheckoutClient;
import br.com.alc.ecommerce.channel.infrastructure.dto.order.OrderRequestDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

import static br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil.generateJson;

@Log4j2
@Component
@AllArgsConstructor
public class OrderInvoicerOutPortImpl implements OrderInvoicerOutPort {

    private final EcommerceCheckoutClient ecommerceCheckoutClient;
    private final ModelMapper modelMapper;

    @Override
    @CircuitBreaker(name = "order-invoicer-circuitbreaker")
    public Optional<String> execute(OrderRequest orderRequest) {
        log.debug("Incoming into SaleAuthorizerOutPortImpl: {}", generateJson(orderRequest));
        OrderRequestDto orderRequestDto = modelMapper.map(orderRequest, OrderRequestDto.class);
        log.info("---> Request /authorize-sale: {}", generateJson(orderRequestDto));
        Map<String, String> responseMap = ecommerceCheckoutClient.authorizeSale(orderRequestDto);
        log.info("<--- Response /authorize-sale: {}", generateJson(responseMap));
        Optional<String> errorReason = buildErrorReason(responseMap);
        log.debug("Outgoing from SaleAuthorizerOutPortImpl: {}", generateJson(errorReason));
        return errorReason;
    }

    private Optional<String> buildErrorReason(Map<String, String> responseMap) {
        return Optional.ofNullable(responseMap.get("message"));
    }
}