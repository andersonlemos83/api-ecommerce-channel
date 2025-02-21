package br.com.alc.ecommerce.channel.core.port.output;

import br.com.alc.ecommerce.channel.core.domain.order.OrderRequest;
import br.com.alc.ecommerce.channel.core.domain.order.OrderResponse;

public interface OrderInvoicerOutPort {

    OrderResponse execute(OrderRequest orderRequest);

}