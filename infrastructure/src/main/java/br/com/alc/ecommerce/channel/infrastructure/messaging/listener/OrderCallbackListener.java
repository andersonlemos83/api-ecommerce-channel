package br.com.alc.ecommerce.channel.infrastructure.messaging.listener;

import br.com.alc.ecommerce.channel.infrastructure.adapter.input.OrderCallbackProcessorInAdapter;
import br.com.alc.ecommerce.channel.infrastructure.dto.callback.OrderCallbackRequestDto;
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
public class OrderCallbackListener {

    private final OrderCallbackProcessorInAdapter orderCallbackProcessorInAdapter;

    @RabbitListener(queues = {"${spring.rabbitmq.sale-callback-queue}"})
    public void processOrderCallback(OrderCallbackRequestDto orderCallbackRequestDto) {
        try {
            log.info("---> Listener of the sale-callback-queue: {}", generateJson(orderCallbackRequestDto));
            orderCallbackProcessorInAdapter.execute(orderCallbackRequestDto);
            log.info("<--- Listener of the sale-callback-queue processed successfully");
        } catch (Exception exception) {
            log.error("<--- Error in the listener of the sale-callback-queue: {}", getMessage(exception), exception);
        }
    }
}