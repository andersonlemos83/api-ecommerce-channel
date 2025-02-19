package br.com.alc.ecommerce.channel.infrastructure.adapter.input.impl;

import br.com.alc.ecommerce.channel.core.domain.bot.OrderBotRequest;
import br.com.alc.ecommerce.channel.core.port.input.OrderNumberGeneratorUseCase;
import br.com.alc.ecommerce.channel.infrastructure.adapter.input.OrderNumberGeneratorInAdapter;
import br.com.alc.ecommerce.channel.infrastructure.dto.bot.OrderBotRequestDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import static br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil.generateJson;

@Log4j2
@Component
@AllArgsConstructor
public class OrderNumberGeneratorInAdapterImpl implements OrderNumberGeneratorInAdapter {

    private final OrderNumberGeneratorUseCase orderNumberGeneratorUseCase;
    private final ModelMapper modelMapper;

    @Override
    public Flux<String> execute(OrderBotRequestDto orderBotRequestDto) {
        return Flux.just(orderBotRequestDto)
                .doOnNext(in -> log.debug("Incoming into OrderNumberGeneratorInAdapterImpl: {}", generateJson(in)))
                .map(this::buildOrderBotRequest)
                .flatMap(orderNumberGeneratorUseCase::execute)
                .doOnNext(out -> log.debug("Outgoing from OrderNumberGeneratorInAdapterImpl: {}", generateJson(out)));
    }

    private OrderBotRequest buildOrderBotRequest(OrderBotRequestDto orderBotRequestDto) {
        return modelMapper.map(orderBotRequestDto, OrderBotRequest.class);
    }
}