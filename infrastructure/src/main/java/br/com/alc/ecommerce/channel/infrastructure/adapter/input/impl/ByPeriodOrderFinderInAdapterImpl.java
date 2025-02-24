package br.com.alc.ecommerce.channel.infrastructure.adapter.input.impl;

import br.com.alc.ecommerce.channel.core.domain.finder.OrderFinderRequest;
import br.com.alc.ecommerce.channel.core.domain.order.Order;
import br.com.alc.ecommerce.channel.core.port.input.ByPeriodOrderFinderUseCase;
import br.com.alc.ecommerce.channel.infrastructure.adapter.input.ByPeriodOrderFinderInAdapter;
import br.com.alc.ecommerce.channel.infrastructure.dto.finder.HalfOrderFinderResponseDto;
import br.com.alc.ecommerce.channel.infrastructure.dto.finder.OrderFinderRequestDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import static br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil.generateJson;

@Log4j2
@Component
@AllArgsConstructor
public class ByPeriodOrderFinderInAdapterImpl implements ByPeriodOrderFinderInAdapter {

    private final ByPeriodOrderFinderUseCase byPeriodOrderFinderUseCase;
    private final ModelMapper looseModelMapper;

    @Override
    public Flux<HalfOrderFinderResponseDto> execute(OrderFinderRequestDto orderFinderRequestDto) {
        return Flux.just(orderFinderRequestDto)
                .doOnNext(in -> log.debug("Incoming into ByPeriodOrderFinderInAdapterImpl: {}", generateJson(in)))
                .map(this::buildOrderFinderRequest)
                .flatMap(byPeriodOrderFinderUseCase::execute)
                .map(this::buildHalfOrderFinderResponseDto)
                .doOnNext(out -> log.debug("Outgoing from ByPeriodOrderFinderInAdapterImpl: {}", generateJson(out)));
    }

    private OrderFinderRequest buildOrderFinderRequest(OrderFinderRequestDto orderFinderRequestDto) {
        return looseModelMapper.map(orderFinderRequestDto, OrderFinderRequest.class);
    }

    private HalfOrderFinderResponseDto buildHalfOrderFinderResponseDto(Order order) {
        return looseModelMapper.map(order, HalfOrderFinderResponseDto.class);
    }
}