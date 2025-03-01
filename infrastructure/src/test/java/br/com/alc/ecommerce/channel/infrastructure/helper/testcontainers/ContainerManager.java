package br.com.alc.ecommerce.channel.infrastructure.helper.testcontainers;

public interface ContainerManager {

    void start();

    void stop();

    void restart();

    boolean isRunning();

}