package br.com.alc.ecommerce.channel.core.port.output;

import br.com.alc.ecommerce.channel.core.domain.order.Order;
import reactor.core.publisher.Mono;

public interface ByOrderNumberOrderFinderOutPort {

    Mono<Order> execute(String orderNumber);

}