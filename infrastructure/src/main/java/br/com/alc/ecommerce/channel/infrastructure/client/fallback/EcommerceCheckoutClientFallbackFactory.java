package br.com.alc.ecommerce.channel.infrastructure.client.fallback;

import br.com.alc.ecommerce.channel.core.exception.DefaultOutPortException;
import br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil;
import br.com.alc.ecommerce.channel.infrastructure.client.EcommerceCheckoutClient;
import br.com.alc.ecommerce.channel.infrastructure.dto.order.OrderResponseDto;
import feign.FeignException;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

import static br.com.alc.ecommerce.channel.core.domain.order.SaleStatus.ERROR;
import static org.apache.commons.lang3.exception.ExceptionUtils.getMessage;

@Log4j2
@Component
@SuppressWarnings("squid:S7091")
public class EcommerceCheckoutClientFallbackFactory implements FallbackFactory<EcommerceCheckoutClient> {

    @Override
    public EcommerceCheckoutClient create(Throwable throwable) {
        log.error("Error in the EcommerceCheckoutClientFallbackFactory: {}", getMessage(throwable), throwable);
        if (throwable instanceof FeignException.BadRequest exception) {
            Map<String, Object> properties = ObjectMapperUtil.generateMap(exception.contentUTF8());
            String message = Optional.ofNullable(properties.get("message"))
                    .map(String.class::cast)
                    .orElse(exception.getMessage());
            throw new DefaultOutPortException(message, exception.getCause());
        }
        return orderRequestDto -> OrderResponseDto.builder().status(ERROR).build();
    }
}