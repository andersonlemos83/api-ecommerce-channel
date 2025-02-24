package br.com.alc.ecommerce.channel.core.port.input.impl;

import br.com.alc.ecommerce.channel.core.domain.finder.OrderFinderRequest;
import br.com.alc.ecommerce.channel.core.domain.order.Order;
import br.com.alc.ecommerce.channel.core.port.input.ByPeriodOrderFinderUseCase;
import br.com.alc.ecommerce.channel.core.port.output.ByPeriodOrderFinderFinderOutPort;
import br.com.alc.ecommerce.channel.core.service.validator.ByPeriodOrderFinderValidatorService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

import static br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil.generateJson;

@Log4j2
@AllArgsConstructor
public final class ByPeriodOrderFinderUseCaseImpl implements ByPeriodOrderFinderUseCase {

    private final ByPeriodOrderFinderValidatorService byPeriodOrderFinderValidatorService;
    private final ByPeriodOrderFinderFinderOutPort byPeriodOrderFinderFinderOutPort;

    @Override
    public Flux<Order> execute(OrderFinderRequest orderFinderRequest) {
        return Flux.just(orderFinderRequest)
                .doOnNext(in -> log.info("Incoming into ByPeriodOrderFinderUseCaseImpl: {}", generateJson(in)))
                .flatMap(byPeriodOrderFinderValidatorService::validate)
                .thenMany(byPeriodOrderFinderFinderOutPort.execute(orderFinderRequest))
                .doOnNext(out -> log.info("Outgoing from ByPeriodOrderFinderUseCaseImpl: {}", generateJson(out)));
    }
}