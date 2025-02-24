package br.com.alc.ecommerce.channel.core.port.input;

import br.com.alc.ecommerce.channel.core.domain.finder.OrderFinderRequest;
import br.com.alc.ecommerce.channel.core.domain.order.Order;
import reactor.core.publisher.Flux;

public interface ByPeriodOrderFinderUseCase {

    Flux<Order> execute(OrderFinderRequest orderFinderRequest);

}