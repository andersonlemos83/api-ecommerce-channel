package br.com.alc.ecommerce.channel.infrastructure.adapter.input;

import br.com.alc.ecommerce.channel.infrastructure.dto.order.OrderRequestDto;

public interface OrderProcessorInAdapter {

    void execute(OrderRequestDto orderRequestDto);

}