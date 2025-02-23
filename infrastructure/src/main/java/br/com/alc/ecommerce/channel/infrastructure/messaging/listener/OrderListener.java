package br.com.alc.ecommerce.channel.infrastructure.messaging.listener;

import br.com.alc.ecommerce.channel.infrastructure.adapter.input.OrderProcessorInAdapter;
import br.com.alc.ecommerce.channel.infrastructure.dto.order.OrderRequestDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil.generateJson;
import static org.apache.commons.lang3.exception.ExceptionUtils.getMessage;

@Log4j2
@Service
@Transactional
@AllArgsConstructor
public class OrderListener {

    private final OrderProcessorInAdapter orderProcessorInAdapter;

    @RabbitListener(queues = {"${spring.rabbitmq.order-queue}"})
    public void processOrder(OrderRequestDto orderRequestDto) {
        try {
            log.info("---> Listener of the order-queue: {}", generateJson(orderRequestDto));
            orderProcessorInAdapter.execute(orderRequestDto);
            log.info("<--- Listener of the order-queue processed successfully");
        } catch (Exception exception) {
            log.error("<--- Error in the listener of the order-queue: {}", getMessage(exception), exception);
        }
    }
}