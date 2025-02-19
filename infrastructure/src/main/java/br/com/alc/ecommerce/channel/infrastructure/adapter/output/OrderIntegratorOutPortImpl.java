package br.com.alc.ecommerce.channel.infrastructure.adapter.output;

import br.com.alc.ecommerce.channel.core.domain.order.OrderRequest;
import br.com.alc.ecommerce.channel.core.port.output.OrderIntegratorOutPort;
import br.com.alc.ecommerce.channel.infrastructure.dto.order.OrderRequestDto;
import br.com.alc.ecommerce.channel.infrastructure.messaging.producer.MessagingProducer;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil.generateJson;

@Log4j2
@Component
public class OrderIntegratorOutPortImpl implements OrderIntegratorOutPort {

    private final MessagingProducer messagingProducer;
    private final ModelMapper modelMapper;

    private final String orderExchange;
    private final String orderQueue;

    public OrderIntegratorOutPortImpl(MessagingProducer messagingProducer,
                                      ModelMapper modelMapper,
                                      @Value("${spring.rabbitmq.order-exchange}") String orderExchange,
                                      @Value("${spring.rabbitmq.order-queue}") String orderQueue) {
        this.messagingProducer = messagingProducer;
        this.modelMapper = modelMapper;
        this.orderExchange = orderExchange;
        this.orderQueue = orderQueue;
    }

    @Override
    public void execute(OrderRequest orderRequest) {
        log.debug("Incoming into OrderIntegratorOutPortImpl: {}", generateJson(orderRequest));
        OrderRequestDto orderRequestDto = modelMapper.map(orderRequest, OrderRequestDto.class);
        messagingProducer.publish(orderExchange, orderQueue, orderRequestDto);
    }
}