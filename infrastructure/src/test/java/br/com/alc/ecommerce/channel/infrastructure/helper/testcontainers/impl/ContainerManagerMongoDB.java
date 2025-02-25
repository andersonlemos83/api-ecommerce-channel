package br.com.alc.ecommerce.channel.infrastructure.helper.testcontainers.impl;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

import static java.util.Collections.singletonList;

public class ContainerManagerMongoDB extends AbstractContainerManager {

    private static final int MONGO_PORT = 27017;

    @Override
    protected GenericContainer createContainer() {
        MongoDBContainer container = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"))
                .withEnv("MONGO_INITDB_ROOT_USERNAME", "admin")
                .withEnv("MONGO_INITDB_ROOT_PASSWORD", "secret")
                .withEnv("MONGO_INITDB_DATABASE", "ecommerce_db")
                .withPrivilegedMode(true)
                .withReuse(false);
        container.setPortBindings(singletonList(MONGO_PORT + ":" + MONGO_PORT));
        container.withExposedPorts(MONGO_PORT);
        return container;
    }

    @Override
    protected String createContainerName() {
        return "MongoDB";
    }
}