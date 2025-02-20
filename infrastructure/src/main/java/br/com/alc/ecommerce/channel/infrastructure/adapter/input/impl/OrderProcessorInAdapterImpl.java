package br.com.alc.ecommerce.channel.infrastructure.adapter.input.impl;

import br.com.alc.ecommerce.channel.core.domain.order.OrderRequest;
import br.com.alc.ecommerce.channel.core.port.input.OrderProcessorUseCase;
import br.com.alc.ecommerce.channel.infrastructure.adapter.input.OrderProcessorInAdapter;
import br.com.alc.ecommerce.channel.infrastructure.dto.order.OrderRequestDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import static br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil.generateJson;

@Log4j2
@Component
@AllArgsConstructor
public class OrderProcessorInAdapterImpl implements OrderProcessorInAdapter {

    private final OrderProcessorUseCase orderProcessorUseCase;
    private final ModelMapper modelMapper;

    @Override
    public void execute(OrderRequestDto orderRequestDto) {
        log.debug("Incoming into OrderProcessorInAdapterImpl: {}", generateJson(orderRequestDto));
        OrderRequest orderRequest = modelMapper.map(orderRequestDto, OrderRequest.class);
        orderProcessorUseCase.execute(orderRequest);
    }
}