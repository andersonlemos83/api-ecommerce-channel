package br.com.alc.ecommerce.channel.infrastructure.helper.testcontainers.factory;

import br.com.alc.ecommerce.channel.infrastructure.helper.testcontainers.ContainerManager;
import br.com.alc.ecommerce.channel.infrastructure.helper.testcontainers.impl.ContainerManagerMongoDB;
import br.com.alc.ecommerce.channel.infrastructure.helper.testcontainers.impl.ContainerManagerRabbitMQ;
import br.com.alc.ecommerce.channel.infrastructure.helper.testcontainers.impl.ContainerManagerRedis;

import java.util.List;

public interface ContainerFactory {

    ContainerManagerRedis getRedisInstance();

    ContainerManagerMongoDB getMongoDbInstance();

    ContainerManagerRabbitMQ getRabbitMqInstance();

    List<ContainerManager> getInstances();

}