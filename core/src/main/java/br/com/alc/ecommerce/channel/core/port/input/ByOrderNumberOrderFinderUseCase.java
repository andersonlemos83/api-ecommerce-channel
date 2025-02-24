package br.com.alc.ecommerce.channel.core.port.input;

import br.com.alc.ecommerce.channel.core.domain.order.Order;
import reactor.core.publisher.Mono;

public interface ByOrderNumberOrderFinderUseCase {

    Mono<Order> execute(String orderNumber);

}