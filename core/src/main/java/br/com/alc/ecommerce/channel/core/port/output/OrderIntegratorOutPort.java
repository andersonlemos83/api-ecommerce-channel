package br.com.alc.ecommerce.channel.core.port.output;

import br.com.alc.ecommerce.channel.core.domain.order.OrderRequest;

public interface OrderIntegratorOutPort {

    void execute(OrderRequest orderRequest);

}