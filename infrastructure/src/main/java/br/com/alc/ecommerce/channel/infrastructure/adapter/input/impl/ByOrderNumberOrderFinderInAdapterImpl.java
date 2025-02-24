package br.com.alc.ecommerce.channel.infrastructure.adapter.input.impl;

import br.com.alc.ecommerce.channel.core.domain.order.Order;
import br.com.alc.ecommerce.channel.core.port.input.ByOrderNumberOrderFinderUseCase;
import br.com.alc.ecommerce.channel.infrastructure.adapter.input.ByOrderNumberOrderFinderInAdapter;
import br.com.alc.ecommerce.channel.infrastructure.dto.finder.FullOrderFinderResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil.generateJson;

@Log4j2
@Component
@AllArgsConstructor
public class ByOrderNumberOrderFinderInAdapterImpl implements ByOrderNumberOrderFinderInAdapter {

    private final ByOrderNumberOrderFinderUseCase byOrderNumberOrderFinderUseCase;
    private final ModelMapper modelMapper;

    @Override
    public Mono<FullOrderFinderResponseDto> execute(String orderNumber) {
        return Mono.just(orderNumber)
                .doOnNext(in -> log.debug("Incoming into ByOrderNumberOrderFinderInAdapterImpl: {}", generateJson(in)))
                .flatMap(byOrderNumberOrderFinderUseCase::execute)
                .map(this::buildOrderFinderResponseDto)
                .doOnNext(out -> log.debug("Outgoing from ByOrderNumberOrderFinderInAdapterImpl: {}", generateJson(out)));
    }

    private FullOrderFinderResponseDto buildOrderFinderResponseDto(Order order) {
        return modelMapper.map(order, FullOrderFinderResponseDto.class);
    }
}