package br.com.alc.ecommerce.channel.infrastructure.cucumber.feature;

import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.generator.OrderGeneratorRequestDataTable;
import br.com.alc.ecommerce.channel.infrastructure.helper.manager.RabbitMqManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProcessOrderGenerationFeature {

    private final RabbitMqManager rabbitMqManager;

    private final String orderBotExchange;
    private final String orderGeneratorQueue;

    @Autowired
    public ProcessOrderGenerationFeature(RabbitMqManager rabbitMqManager,
                                         @Value("${spring.rabbitmq.order-bot-exchange}") String orderBotExchange,
                                         @Value("${spring.rabbitmq.order-generator-queue}") String orderGeneratorQueue) {
        this.rabbitMqManager = rabbitMqManager;
        this.orderBotExchange = orderBotExchange;
        this.orderGeneratorQueue = orderGeneratorQueue;
    }

    public void execute(OrderGeneratorRequestDataTable orderGeneratorRequestDataTable) {
        rabbitMqManager.enableListener(orderGeneratorQueue);
        rabbitMqManager.sendMessage(orderBotExchange, orderGeneratorQueue, orderGeneratorRequestDataTable);
    }
}