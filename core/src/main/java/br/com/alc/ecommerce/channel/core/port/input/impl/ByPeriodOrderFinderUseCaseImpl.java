package br.com.alc.ecommerce.channel.core.port.input.impl;

import br.com.alc.ecommerce.channel.core.domain.finder.OrderFinderRequest;
import br.com.alc.ecommerce.channel.core.domain.order.Order;
import br.com.alc.ecommerce.channel.core.port.input.ByPeriodOrderFinderUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

@Log4j2
@AllArgsConstructor
public class ByPeriodOrderFinderUseCaseImpl implements ByPeriodOrderFinderUseCase {

    @Override
    public Flux<Order> execute(OrderFinderRequest orderFinderRequest) {
        return null;
    }
}