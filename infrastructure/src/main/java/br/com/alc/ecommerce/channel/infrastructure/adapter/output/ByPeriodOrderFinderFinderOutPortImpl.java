package br.com.alc.ecommerce.channel.infrastructure.adapter.output;

import br.com.alc.ecommerce.channel.core.domain.finder.OrderFinderRequest;
import br.com.alc.ecommerce.channel.core.domain.order.Order;
import br.com.alc.ecommerce.channel.core.port.output.ByPeriodOrderFinderFinderOutPort;
import br.com.alc.ecommerce.channel.infrastructure.persistence.document.OrderDocument;
import br.com.alc.ecommerce.channel.infrastructure.persistence.repository.OrderReactiveRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import static br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil.generateJson;

@Log4j2
@Component
@AllArgsConstructor
public class ByPeriodOrderFinderFinderOutPortImpl implements ByPeriodOrderFinderFinderOutPort {

    private final OrderReactiveRepository orderReactiveRepository;
    private final ModelMapper modelMapper;

    @Override
    public Flux<Order> execute(OrderFinderRequest orderFinderRequest) {
        return Flux.just(orderFinderRequest)
                .doOnNext(in -> log.debug("Incoming into ByPeriodOrderFinderFinderOutPortImpl: {}", generateJson(in)))
                .flatMap(this::findAllOrders)
                .map(this::buildOrderFinderResponse)
                .doOnNext(out -> log.debug("Outgoing from ByPeriodOrderFinderFinderOutPortImpl: {}", generateJson(out)));
    }

    private Flux<OrderDocument> findAllOrders(OrderFinderRequest orderFinderRequest) {
        Pageable pageable = PageRequest.of(orderFinderRequest.getPageNumber(), orderFinderRequest.getPageSize());
        return orderReactiveRepository.findAllByCreatedDateBetweenOrderByCreatedDateDesc(orderFinderRequest.generateStartPeriodLocalDateTime(),
                orderFinderRequest.generateEndPeriodLocalDateTime(), pageable);
    }

    private Order buildOrderFinderResponse(OrderDocument orderDocument) {
        return modelMapper.map(orderDocument, Order.class);
    }
}