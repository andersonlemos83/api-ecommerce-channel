package br.com.alc.ecommerce.channel.infrastructure.messaging.producer.impl;

import br.com.alc.ecommerce.channel.infrastructure.messaging.producer.MessagingProducer;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil.generateJson;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.amqp.core.MessageProperties.CONTENT_TYPE_JSON;

@Log4j2
@Component
@AllArgsConstructor
public class MessagingProducerImpl implements MessagingProducer {

    private final RabbitTemplate rabbitTemplate;

//    @Async
    @Override
    public void publish(String exchange, String queue, Object request) {
        try {
            final String content = generateJson(request);
            log.info("---> Sending to queue {}.{} - {}", exchange, queue, content);
            rabbitTemplate.convertAndSend(exchange, queue, buildMessage(content));
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
        }
    }

//    @Async
    @Override
    public void publish(String queue, Object request) {
        try {
            final String content = generateJson(request);
            log.info("---> Sending to queue {} - {}", queue, content);
            rabbitTemplate.convertAndSend(queue, buildMessage(content));
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
        }
    }

    private Message buildMessage(String content) {
        return MessageBuilder.withBody(content.getBytes(UTF_8))
                .setContentType(CONTENT_TYPE_JSON)
                .build();
    }
}