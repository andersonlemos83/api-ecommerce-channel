package br.com.alc.ecommerce.channel.infrastructure.helper.manager.impl;

import br.com.alc.ecommerce.channel.infrastructure.helper.manager.RabbitMqManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.UncategorizedAmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static com.fasterxml.jackson.databind.DeserializationFeature.*;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Comparator.naturalOrder;
import static org.springframework.amqp.core.MessageProperties.CONTENT_TYPE_JSON;

@Log4j2
@Component
@AllArgsConstructor
@SuppressWarnings("squid:S2925") // "Thread.sleep" should not be used in tests
public class RabbitMqManagerImpl implements RabbitMqManager {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setTimeZone(TimeZone.getDefault());
        objectMapper.disable(WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(ACCEPT_FLOAT_AS_INT);
        objectMapper.disable(FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.disable(FAIL_ON_MISSING_CREATOR_PROPERTIES);
        objectMapper.enable(ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        objectMapper.registerModule(new JavaTimeModule());
    }

    private final RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;
    private final RabbitAdmin rabbitAdmin;
    private final RabbitTemplate rabbitTemplate;

    public void disableAllListeners() {
        rabbitListenerEndpointRegistry.getListenerContainers().forEach(MessageListenerContainer::stop);
    }

    public void enableAllListeners() {
        rabbitListenerEndpointRegistry.getListenerContainers().forEach(MessageListenerContainer::start);
    }

    public void disableListener(final String queueName) {
        rabbitListenerEndpointRegistry.getListenerContainers()
                .stream()
                .map(SimpleMessageListenerContainer.class::cast)
                .filter(listener -> asList(listener.getQueueNames()).contains(queueName))
                .forEach(MessageListenerContainer::stop);
    }

    public void enableListener(final String queueName) {
        rabbitListenerEndpointRegistry.getListenerContainers()
                .stream()
                .map(SimpleMessageListenerContainer.class::cast)
                .filter(listener -> asList(listener.getQueueNames()).contains(queueName))
                .forEach(MessageListenerContainer::start);
    }

    public void clearQueues(final List<String> queueNames) {
        queueNames.forEach(this::clearQueue);
    }

    public void clearQueue(final String queueName) {
        if (queueExists(queueName)) {
            rabbitAdmin.purgeQueue(queueName);
        }
    }

    public List<String> getMessages(final String queueName) {
        try {
            if (!queueExists(queueName)) {
                return emptyList();
            }
            List<Message> mensagens = new ArrayList<>();
            Integer messageCount = getMessageCount(queueName);
            for (int i = 0; i < messageCount; i++) {
                TimeUnit.MILLISECONDS.sleep(10);
                Message message = rabbitTemplate.receive(queueName);
                if (message != null) {
                    mensagens.add(message);
                }
            }
            return mensagens.stream()
                    .map(gerarJsonMessageFunction())
                    .sorted(naturalOrder())
                    .toList();
        } catch (InterruptedException | IllegalArgumentException | UncategorizedAmqpException exception) {
            log.error("Error getMessages: " + exception.getMessage());
            return emptyList();
        }
    }

    public void sendMessage(String exchange, String queue, Object request) {
        try {
            final String content = objectMapper.writeValueAsString(request);
            log.info("---> Sending to queue {}.{} - {}", exchange, queue, content);
            final Message message = MessageBuilder.withBody(content.getBytes(UTF_8)).setContentType(CONTENT_TYPE_JSON).build();
            rabbitTemplate.convertSendAndReceive(exchange, queue, message);
        } catch (final Exception exception) {
            log.error("Error sendMessage: " + exception.getMessage(), exception);
        }
    }

    public void sendMessage(String queue, Object request) {
        try {
            final String content = objectMapper.writeValueAsString(request);
            log.info("---> Sending to queue {} - {}", queue, content);
            final Message message = MessageBuilder
                    .withBody(content.getBytes(UTF_8))
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                    .build();
            rabbitTemplate.convertSendAndReceive(queue, message);
        } catch (final Exception exception) {
            log.error("Error sendMessage: " + exception.getMessage(), exception);
        }
    }

    public Properties getQueueConfigurations(String queueName) {
        return rabbitAdmin.getQueueProperties(queueName);
    }

    public boolean queueExists(String queueName) {
        return getQueueConfigurations(queueName) != null;
    }

    public Integer getMessageCount(String queueName) {
        return (Integer) rabbitAdmin.getQueueProperties(queueName).get("QUEUE_MESSAGE_COUNT");
    }

    private Function<Message, String> gerarJsonMessageFunction() {
        return message -> new String(message.getBody(), UTF_8);
    }
}