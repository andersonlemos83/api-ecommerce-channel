package br.com.alc.ecommerce.channel.core.port.output;

import br.com.alc.ecommerce.channel.core.domain.finder.OrderFinderRequest;
import br.com.alc.ecommerce.channel.core.domain.order.Order;
import reactor.core.publisher.Flux;

public interface ByPeriodOrderFinderFinderOutPort {

    Flux<Order> execute(OrderFinderRequest orderFinderRequest);

}