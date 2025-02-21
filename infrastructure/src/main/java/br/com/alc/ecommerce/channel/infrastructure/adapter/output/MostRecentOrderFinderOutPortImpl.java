package br.com.alc.ecommerce.channel.infrastructure.adapter.output;

import br.com.alc.ecommerce.channel.core.domain.order.Order;
import br.com.alc.ecommerce.channel.core.port.output.MostRecentOrderFinderOutPort;
import br.com.alc.ecommerce.channel.infrastructure.persistence.document.OrderDocument;
import br.com.alc.ecommerce.channel.infrastructure.persistence.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil.generateJson;

@Log4j2
@Component
@AllArgsConstructor
public class MostRecentOrderFinderOutPortImpl implements MostRecentOrderFinderOutPort {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    public Optional<Order> execute(String orderNumber) {
        log.debug("Incoming into MostRecentOrderFinderOutPortImpl: {}", generateJson(orderNumber));
        Optional<OrderDocument> orderOptional = orderRepository.findFirstByOrderRequest_OrderNumberOrderByUpdatedDateDesc(orderNumber);
        Optional<Order> order = orderOptional.map(this::buildOrder);
        order.ifPresent(out -> log.debug("Outgoing from MostRecentOrderFinderOutPortImpl: {}", generateJson(out)));
        return order;
    }

    private Order buildOrder(OrderDocument orderDocument) {
        return modelMapper.map(orderDocument, Order.class);
    }
}