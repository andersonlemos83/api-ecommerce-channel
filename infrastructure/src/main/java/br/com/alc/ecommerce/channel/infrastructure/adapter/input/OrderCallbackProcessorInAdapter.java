package br.com.alc.ecommerce.channel.infrastructure.adapter.input;

import br.com.alc.ecommerce.channel.infrastructure.dto.callback.OrderCallbackRequestDto;

public interface OrderCallbackProcessorInAdapter {

    void execute(OrderCallbackRequestDto orderCallbackRequestDto);

}