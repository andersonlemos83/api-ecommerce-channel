package br.com.alc.ecommerce.channel.infrastructure.adapter.output;

import br.com.alc.ecommerce.channel.core.domain.order.Order;
import br.com.alc.ecommerce.channel.core.port.output.OrderInserterOutPort;
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
public class OrderInserterOutPortImpl implements OrderInserterOutPort {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    public void execute(Order order) {
        log.debug("Incoming into OrderInserterOutPortImpl: {}", generateJson(order));
        OrderDocument orderDocument = modelMapper.map(order, OrderDocument.class);
        Optional<OrderDocument> orderOptional = orderRepository.findFirstByOrderRequest_OrderNumberOrderByUpdatedDateDesc(order.getOrderNumber());
        orderOptional.ifPresent(document -> orderDocument.setId(document.getId()));
        OrderDocument insertedOrderDocument = orderRepository.save(orderDocument);
        log.debug("Outgoing from OrderInserterOutPortImpl: {}", generateJson(insertedOrderDocument));
    }
}