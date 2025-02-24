package br.com.alc.ecommerce.channel.core.port.input.impl;

import br.com.alc.ecommerce.channel.core.domain.order.Order;
import br.com.alc.ecommerce.channel.core.exception.OrderNotFoundException;
import br.com.alc.ecommerce.channel.core.port.input.ByOrderNumberOrderFinderUseCase;
import br.com.alc.ecommerce.channel.core.port.output.ByOrderNumberOrderFinderOutPort;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

import static br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil.generateJson;

@Log4j2
@AllArgsConstructor
public final class ByOrderNumberOrderFinderUseCaseImpl implements ByOrderNumberOrderFinderUseCase {

    private final ByOrderNumberOrderFinderOutPort byOrderNumberOrderFinderOutPort;

    @Override
    public Mono<Order> execute(String orderNumber) {
        return Mono.just(orderNumber)
                .doOnNext(in -> log.info("Incoming into ByOrderNumberOrderFinderUseCaseImpl: {}", generateJson(in)))
                .flatMap(byOrderNumberOrderFinderOutPort::execute)
                .switchIfEmpty(Mono.error(OrderNotFoundException::new))
                .doOnNext(out -> log.info("Outgoing from ByOrderNumberOrderFinderUseCaseImpl: {}", generateJson(out)));
    }
}