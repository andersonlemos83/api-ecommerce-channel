package br.com.alc.ecommerce.channel.core.port.input;

import br.com.alc.ecommerce.channel.core.domain.order.OrderRequest;

public interface OrderProcessorUseCase {

    void execute(OrderRequest orderRequest);

}