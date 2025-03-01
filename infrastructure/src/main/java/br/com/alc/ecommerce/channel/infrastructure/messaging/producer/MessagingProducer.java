package br.com.alc.ecommerce.channel.infrastructure.messaging.producer;

public interface MessagingProducer {

    void publish(String exchange, String queue, Object request);

}