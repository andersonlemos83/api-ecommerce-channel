package br.com.alc.ecommerce.channel.infrastructure.adapter.input.impl;

import br.com.alc.ecommerce.channel.core.domain.callback.OrderCallbackRequest;
import br.com.alc.ecommerce.channel.core.port.input.OrderCallbackProcessorUseCase;
import br.com.alc.ecommerce.channel.infrastructure.adapter.input.OrderCallbackProcessorInAdapter;
import br.com.alc.ecommerce.channel.infrastructure.dto.callback.OrderCallbackRequestDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import static br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil.generateJson;

@Log4j2
@Component
@AllArgsConstructor
public class OrderCallbackProcessorInAdapterImpl implements OrderCallbackProcessorInAdapter {

    private final OrderCallbackProcessorUseCase orderCallbackProcessorUseCase;
    private final ModelMapper modelMapper;

    @Override
    public void execute(OrderCallbackRequestDto orderCallbackRequestDto) {
        log.debug("Incoming into OrderCallbackProcessorInAdapterImpl: {}", generateJson(orderCallbackRequestDto));
        OrderCallbackRequest orderCallbackRequest = modelMapper.map(orderCallbackRequestDto, OrderCallbackRequest.class);
        orderCallbackProcessorUseCase.execute(orderCallbackRequest);
    }
}