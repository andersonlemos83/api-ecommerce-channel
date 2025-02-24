package br.com.alc.ecommerce.channel.infrastructure.adapter.output;

import br.com.alc.ecommerce.channel.core.domain.order.Order;
import br.com.alc.ecommerce.channel.core.port.output.ByOrderNumberOrderFinderOutPort;
import br.com.alc.ecommerce.channel.infrastructure.persistence.document.OrderDocument;
import br.com.alc.ecommerce.channel.infrastructure.persistence.repository.OrderReactiveRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil.generateJson;

@Log4j2
@Component
@AllArgsConstructor
public class ByOrderNumberOrderFinderOutPortImpl implements ByOrderNumberOrderFinderOutPort {

    private final OrderReactiveRepository orderReactiveRepository;
    private final ModelMapper modelMapper;

    @Override
    public Mono<Order> execute(String orderNumber) {
        return Mono.just(orderNumber)
                .doOnNext(in -> log.debug("Incoming into ByOrderNumberOrderFinderOutPortImpl: {}", generateJson(in)))
                .flatMap(orderReactiveRepository::findFirstByOrderRequest_OrderNumberOrderByUpdatedDateDesc)
                .map(this::buildOrderFinderResponse)
                .doOnNext(out -> log.debug("Outgoing from ByOrderNumberOrderFinderOutPortImpl: {}", generateJson(out)));
    }

    private Order buildOrderFinderResponse(OrderDocument orderDocument) {
        return modelMapper.map(orderDocument, Order.class);
    }
}