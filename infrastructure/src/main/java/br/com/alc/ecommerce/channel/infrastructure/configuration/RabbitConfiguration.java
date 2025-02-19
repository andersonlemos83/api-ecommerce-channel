package br.com.alc.ecommerce.channel.infrastructure.configuration;

import br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    @Value("${spring.rabbitmq.order-bot-exchange}")
    private String orderBotExchange;

    @Value("${spring.rabbitmq.order-generator-queue}")
    private String orderGeneratorQueueName;

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter(ObjectMapperUtil.getInstance().copy().findAndRegisterModules());
    }

    @Bean
    public TopicExchange orderBotExchange() {
        return ExchangeBuilder.topicExchange(orderBotExchange).build();
    }

    @Bean
    public Queue orderGeneratorQueue() {
        return QueueBuilder.durable(orderGeneratorQueueName).build();
    }

    @Bean
    public Binding orderGeneratorQueueBinding(TopicExchange orderBotExchange, Queue orderGeneratorQueue) {
        return BindingBuilder.bind(orderGeneratorQueue).to(orderBotExchange).with(orderGeneratorQueueName);
    }
}