package br.com.alc.ecommerce.channel.infrastructure.cucumber.feature;

import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.order.OrderRequestDataTable;
import br.com.alc.ecommerce.channel.infrastructure.helper.manager.RabbitMqManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProcessOrderFeature {

    private final RabbitMqManager rabbitMqManager;

    private final String orderExchange;
    private final String orderQueue;

    @Autowired
    public ProcessOrderFeature(RabbitMqManager rabbitMqManager,
                               @Value("${spring.rabbitmq.order-exchange}") String orderExchange,
                               @Value("${spring.rabbitmq.order-queue}") String orderQueue) {
        this.rabbitMqManager = rabbitMqManager;
        this.orderExchange = orderExchange;
        this.orderQueue = orderQueue;
    }

    public void execute(OrderRequestDataTable orderRequestDataTable) {
        rabbitMqManager.enableListener(orderQueue);
        rabbitMqManager.sendMessage(orderExchange, orderQueue, orderRequestDataTable);
    }
}