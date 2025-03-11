package br.com.alc.ecommerce.channel.infrastructure.client.fallback;

import br.com.alc.ecommerce.channel.core.exception.DefaultOutPortException;
import br.com.alc.ecommerce.channel.infrastructure.client.ViaCepClient;
import feign.FeignException;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.apache.commons.lang3.exception.ExceptionUtils.getMessage;

@Log4j2
@Component
@SuppressWarnings("squid:S7091") // Circular dependencies between classes across packages
public class ViaCepClientFallbackFactory implements FallbackFactory<ViaCepClient> {

    @Override
    public ViaCepClient create(Throwable throwable) {
        log.error("Error in the ViaCepClientFallbackFactory: {}", getMessage(throwable), throwable);
        throw Optional.ofNullable(throwable)
                .filter(FeignException.BadRequest.class::isInstance)
                .map(FeignException.BadRequest.class::cast)
                .map(badRequest -> new DefaultOutPortException(badRequest.contentUTF8(), badRequest.getCause()))
                .orElse(new DefaultOutPortException(throwable.getMessage(), throwable.getCause()));
    }
}