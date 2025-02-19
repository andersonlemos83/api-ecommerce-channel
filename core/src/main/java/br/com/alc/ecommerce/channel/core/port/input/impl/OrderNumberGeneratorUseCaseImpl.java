package br.com.alc.ecommerce.channel.core.port.input.impl;

import br.com.alc.ecommerce.channel.core.domain.bot.OrderBotRequest;
import br.com.alc.ecommerce.channel.core.domain.generator.OrderGeneratorRequest;
import br.com.alc.ecommerce.channel.core.port.input.OrderNumberGeneratorUseCase;
import br.com.alc.ecommerce.channel.core.port.output.OrderNumberIntegratorOutPort;
import br.com.alc.ecommerce.channel.core.service.OrderNumberService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

import static br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil.generateJson;

@Log4j2
@AllArgsConstructor
public class OrderNumberGeneratorUseCaseImpl implements OrderNumberGeneratorUseCase {

    private final OrderNumberService orderNumberService;
    private final OrderNumberIntegratorOutPort orderNumberIntegratorOutPort;

    @Override
    public Flux<String> execute(OrderBotRequest orderBotRequest) {
        return Flux.just(orderBotRequest)
                .doOnNext(in -> log.info("Incoming into OrderNumberGeneratorUseCaseImpl: {}", generateJson(in)))
                .flatMap(orderNumberService::execute)
                .flatMap(this::integrateOrderNumber)
                .doOnNext(out -> log.info("Outgoing from OrderNumberGeneratorUseCaseImpl: {}", generateJson(out)));
    }

    private Flux<String> integrateOrderNumber(String orderNumber) {
        return Flux.just(orderNumber)
                .map(this::buildOrderNumberRequest)
                .flatMap(request -> orderNumberIntegratorOutPort.execute(request))
                .map(object -> orderNumber);
    }

    private OrderGeneratorRequest buildOrderNumberRequest(String orderNumber) {
        return OrderGeneratorRequest.builder()
                .orderNumber(orderNumber)
                .build();
    }
}