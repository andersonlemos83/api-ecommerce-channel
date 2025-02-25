package br.com.alc.ecommerce.channel.infrastructure.helper.testcontainers.factory.impl;

import br.com.alc.ecommerce.channel.infrastructure.helper.testcontainers.ContainerManager;
import br.com.alc.ecommerce.channel.infrastructure.helper.testcontainers.factory.ContainerFactory;
import br.com.alc.ecommerce.channel.infrastructure.helper.testcontainers.impl.ContainerManagerMongoDB;
import br.com.alc.ecommerce.channel.infrastructure.helper.testcontainers.impl.ContainerManagerRabbitMQ;
import br.com.alc.ecommerce.channel.infrastructure.helper.testcontainers.impl.ContainerManagerRedis;

import java.util.Arrays;
import java.util.List;

public class ContainerFactoryImpl implements ContainerFactory {

    private static final ContainerManagerRedis CONTAINER_MANAGER_REDIS;
    private static final ContainerManagerMongoDB CONTAINER_MANAGER_MONGO_DB;
    private static final ContainerManagerRabbitMQ CONTAINER_MANAGER_RABBIT_MQ;

    static {
        CONTAINER_MANAGER_REDIS = new ContainerManagerRedis();
        CONTAINER_MANAGER_MONGO_DB = new ContainerManagerMongoDB();
        CONTAINER_MANAGER_RABBIT_MQ = new ContainerManagerRabbitMQ();
    }

    @Override
    public ContainerManagerRedis getRedisInstance() {
        return CONTAINER_MANAGER_REDIS;
    }

    @Override
    public ContainerManagerMongoDB getMongoDbInstance() {
        return CONTAINER_MANAGER_MONGO_DB;
    }

    @Override
    public ContainerManagerRabbitMQ getRabbitMqInstance() {
        return CONTAINER_MANAGER_RABBIT_MQ;
    }

    @Override
    public List<ContainerManager> getInstances() {
        return Arrays.asList(getRedisInstance(), getMongoDbInstance(), getRabbitMqInstance());
    }
}