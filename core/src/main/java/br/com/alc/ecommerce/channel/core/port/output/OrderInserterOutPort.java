package br.com.alc.ecommerce.channel.core.port.output;

import br.com.alc.ecommerce.channel.core.domain.order.Order;

public interface OrderInserterOutPort {

    void execute(Order order);

}