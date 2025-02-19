package br.com.alc.ecommerce.channel.infrastructure.adapter.output;

import br.com.alc.ecommerce.channel.core.domain.generator.OrderGeneratorRequest;
import br.com.alc.ecommerce.channel.core.port.output.OrderNumberIntegratorOutPort;
import br.com.alc.ecommerce.channel.infrastructure.dto.generator.OrderGeneratorRequestDto;
import br.com.alc.ecommerce.channel.infrastructure.messaging.producer.MessagingProducer;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil.generateJson;

@Log4j2
@Component
public class OrderNumberIntegratorOutPortImpl implements OrderNumberIntegratorOutPort {

    private final MessagingProducer messagingProducer;
    private final ModelMapper modelMapper;

    private final String orderBotExchange;
    private final String orderGeneratorQueue;

    public OrderNumberIntegratorOutPortImpl(MessagingProducer messagingProducer,
                                            ModelMapper modelMapper,
                                            @Value("${spring.rabbitmq.order-bot-exchange}") String orderBotExchange,
                                            @Value("${spring.rabbitmq.order-generator-queue}") String orderGeneratorQueue) {
        this.messagingProducer = messagingProducer;
        this.modelMapper = modelMapper;
        this.orderBotExchange = orderBotExchange;
        this.orderGeneratorQueue = orderGeneratorQueue;
    }

    @Override
    public Flux<Void> execute(OrderGeneratorRequest orderGeneratorRequest) {
        return Flux.just(orderGeneratorRequest)
                .doOnNext(in -> log.debug("Incoming into OrderNumberIntegratorOutPortImpl: {}", generateJson(in)))
                .map(this::buildOrderGeneratorRequestDto)
                .flatMap(this::publish);
    }

    private OrderGeneratorRequestDto buildOrderGeneratorRequestDto(OrderGeneratorRequest orderGeneratorRequest) {
        return modelMapper.map(orderGeneratorRequest, OrderGeneratorRequestDto.class);
    }

    private Mono<Void> publish(OrderGeneratorRequestDto orderGeneratorRequestDto) {
        return Mono.fromRunnable(() -> messagingProducer.publish(orderBotExchange, orderGeneratorQueue, orderGeneratorRequestDto));
    }
}