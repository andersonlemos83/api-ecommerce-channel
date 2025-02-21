package br.com.alc.ecommerce.channel.core.port.output;

import br.com.alc.ecommerce.channel.core.domain.order.Order;

import java.util.Optional;

public interface MostRecentOrderFinderOutPort {

    Optional<Order> execute(String orderNumber);

}