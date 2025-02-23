package br.com.alc.ecommerce.channel.core.port.input;

import br.com.alc.ecommerce.channel.core.domain.callback.OrderCallbackRequest;

public interface OrderCallbackProcessorUseCase {

    void execute(OrderCallbackRequest orderCallbackRequest);

}