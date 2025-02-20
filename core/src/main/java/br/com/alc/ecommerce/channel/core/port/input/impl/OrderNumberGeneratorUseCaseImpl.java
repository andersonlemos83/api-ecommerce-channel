package br.com.alc.ecommerce.channel.core.port.input.impl;

import br.com.alc.ecommerce.channel.core.domain.bot.OrderBotRequest;
import br.com.alc.ecommerce.channel.core.domain.bot.OrderBotResponse;
import br.com.alc.ecommerce.channel.core.domain.generator.OrderGeneratorRequest;
import br.com.alc.ecommerce.channel.core.port.input.OrderNumberGeneratorUseCase;
import br.com.alc.ecommerce.channel.core.port.output.OrderNumberIntegratorOutPort;
import br.com.alc.ecommerce.channel.core.service.generator.OrderNumberGeneratorService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

import static br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil.generateJson;

@Log4j2
@AllArgsConstructor
public final class OrderNumberGeneratorUseCaseImpl implements OrderNumberGeneratorUseCase {

    private final OrderNumberGeneratorService orderNumberGeneratorService;
    private final OrderNumberIntegratorOutPort orderNumberIntegratorOutPort;

    @Override
    public Flux<OrderBotResponse> execute(OrderBotRequest orderBotRequest) {
        return Flux.just(orderBotRequest)
                .doOnNext(in -> log.info("Incoming into OrderNumberGeneratorUseCaseImpl: {}", generateJson(in)))
                .flatMap(orderNumberGeneratorService::execute)
                .map(this::buildOrderNumberRequest)
                .flatMap(orderNumberIntegratorOutPort::execute)
                .map(this::buildOrderBotResponse)
                .doOnNext(out -> log.info("Outgoing from OrderNumberGeneratorUseCaseImpl: {}", generateJson(out)));
    }

    private OrderGeneratorRequest buildOrderNumberRequest(String orderNumber) {
        return OrderGeneratorRequest.builder()
                .orderNumber(orderNumber)
                .build();
    }

    private OrderBotResponse buildOrderBotResponse(String orderNumber) {
        return OrderBotResponse.builder()
                .orderNumber(orderNumber)
                .build();
    }
}