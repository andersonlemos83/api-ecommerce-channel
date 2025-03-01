package br.com.alc.ecommerce.channel.infrastructure.helper.testcontainers.impl;

import org.testcontainers.containers.GenericContainer;

import static java.util.Collections.singletonList;

public class ContainerManagerRedis extends AbstractContainerManager {

    private static final int REDIS_PORT = 6379;

    @Override
    protected GenericContainer createContainer() {
        GenericContainer container = new GenericContainer("redis:6.2.13-alpine");
        container.setPortBindings(singletonList(REDIS_PORT + ":" + REDIS_PORT));
        container.withExposedPorts(REDIS_PORT);
        return container;
    }

    @Override
    protected String createContainerName() {
        return "Redis";
    }
}