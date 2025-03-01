package br.com.alc.ecommerce.channel.infrastructure.cucumber.feature;

import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.callback.OrderCallbackRequestDataTable;
import br.com.alc.ecommerce.channel.infrastructure.helper.manager.RabbitMqManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProcessOrderCallbackFeature {

    private final RabbitMqManager rabbitMqManager;

    private final String saleCallbackQueue;

    @Autowired
    public ProcessOrderCallbackFeature(RabbitMqManager rabbitMqManager,
                                       @Value("${spring.rabbitmq.sale-callback-queue}") String saleCallbackQueue) {
        this.rabbitMqManager = rabbitMqManager;
        this.saleCallbackQueue = saleCallbackQueue;
    }

    public void execute(OrderCallbackRequestDataTable orderCallbackRequestDataTable) {
        rabbitMqManager.enableListener(saleCallbackQueue);
        rabbitMqManager.sendMessage(saleCallbackQueue, orderCallbackRequestDataTable);
    }
}