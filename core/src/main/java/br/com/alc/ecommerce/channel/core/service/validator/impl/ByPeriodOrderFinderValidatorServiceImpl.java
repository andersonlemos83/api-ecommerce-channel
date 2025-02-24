package br.com.alc.ecommerce.channel.core.service.validator.impl;

import br.com.alc.ecommerce.channel.core.domain.finder.OrderFinderRequest;
import br.com.alc.ecommerce.channel.core.exception.PeriodInvalidException;
import br.com.alc.ecommerce.channel.core.service.validator.ByPeriodOrderFinderValidatorService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

import static br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil.generateJson;

@Log4j2
@AllArgsConstructor
public final class ByPeriodOrderFinderValidatorServiceImpl implements ByPeriodOrderFinderValidatorService {

    @Override
    public Mono<Void> validate(OrderFinderRequest orderFinderRequest) {
        log.info("Incoming into ByPeriodOrderFinderValidatorServiceImpl: {}", generateJson(orderFinderRequest));
        if (orderFinderRequest.isPeriodInvalid()) {
            return Mono.error(buildPeriodInvalidException(orderFinderRequest));
        }
        return Mono.empty();
    }

    private PeriodInvalidException buildPeriodInvalidException(OrderFinderRequest orderFinderRequest) {
        return new PeriodInvalidException(orderFinderRequest.generateStartPeriodLocalDateTime(), orderFinderRequest.generateEndPeriodLocalDateTime());
    }
}