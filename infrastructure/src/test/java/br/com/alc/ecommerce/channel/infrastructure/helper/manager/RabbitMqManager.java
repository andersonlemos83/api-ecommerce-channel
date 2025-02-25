package br.com.alc.ecommerce.channel.infrastructure.helper.manager;

import java.util.List;
import java.util.Properties;

public interface RabbitMqManager {

    void disableAllListeners();

    void enableAllListeners();

    void disableListener(String queueName);

    void enableListener(String queueName);

    void clearQueues(List<String> queueNames);

    void clearQueue(String queueName);

    List<String> getMessages(String queueName);

    void sendMessage(String exchange, String queue, Object request);

    void sendMessage(String queue, Object request);

    Properties getQueueConfigurations(String queueName);

    boolean queueExists(String queueName);

    Integer getMessageCount(String queueName);

}