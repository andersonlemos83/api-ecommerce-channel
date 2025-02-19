package br.com.alc.ecommerce.channel.infrastructure.adapter.input.impl;

import br.com.alc.ecommerce.channel.core.domain.generator.OrderGeneratorRequest;
import br.com.alc.ecommerce.channel.core.port.input.OrderGeneratorUseCase;
import br.com.alc.ecommerce.channel.infrastructure.adapter.input.OrderGeneratorInAdapter;
import br.com.alc.ecommerce.channel.infrastructure.dto.generator.OrderGeneratorRequestDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import static br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil.generateJson;

@Log4j2
@Component
@AllArgsConstructor
public class OrderGeneratorInAdapterImpl implements OrderGeneratorInAdapter {

    private final OrderGeneratorUseCase orderGeneratorUseCase;
    private final ModelMapper modelMapper;

    @Override
    public void execute(OrderGeneratorRequestDto orderGeneratorRequestDto) {
        log.debug("Incoming into OrderGeneratorInAdapterImpl: {}", generateJson(orderGeneratorRequestDto));
        OrderGeneratorRequest orderGeneratorRequest = modelMapper.map(orderGeneratorRequestDto, OrderGeneratorRequest.class);
        orderGeneratorUseCase.execute(orderGeneratorRequest);
    }
}