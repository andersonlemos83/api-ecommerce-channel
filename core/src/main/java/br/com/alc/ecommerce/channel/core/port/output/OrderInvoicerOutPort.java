package br.com.alc.ecommerce.channel.core.port.output;

import br.com.alc.ecommerce.channel.core.domain.order.OrderRequest;

import java.util.Optional;

public interface OrderInvoicerOutPort {

    Optional<String> execute(OrderRequest orderRequest);

}