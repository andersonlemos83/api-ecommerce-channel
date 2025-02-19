package br.com.alc.ecommerce.channel.infrastructure.messaging.listener;

import br.com.alc.ecommerce.channel.infrastructure.adapter.input.OrderGeneratorInAdapter;
import br.com.alc.ecommerce.channel.infrastructure.dto.generator.OrderGeneratorRequestDto;
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
public class OrderGeneratorListener {

    private final OrderGeneratorInAdapter orderGeneratorInAdapter;

    @RabbitListener(queues = {"${spring.rabbitmq.order-generator-queue}"})
    public void authorizeSale(OrderGeneratorRequestDto orderGeneratorRequestDto) {
        try {
            log.info("---> Listener of the order-generator-queue: {}", generateJson(orderGeneratorRequestDto));
            orderGeneratorInAdapter.execute(orderGeneratorRequestDto);
            log.info("<--- Listener of the order-generator-queue processed successfully");
        } catch (Exception exception) {
            log.error("<--- Error in the listener of the order-generator-queue: {}", getMessage(exception), exception);
        }
    }
}