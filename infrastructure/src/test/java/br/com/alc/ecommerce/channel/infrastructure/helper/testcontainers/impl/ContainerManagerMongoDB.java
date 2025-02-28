package br.com.alc.ecommerce.channel.infrastructure.helper.testcontainers.impl;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.testcontainers.containers.Container;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static java.util.Collections.singletonList;

@Log4j2
@SuppressWarnings("squid:S2925") // "Thread.sleep" should not be used in tests
public class ContainerManagerMongoDB extends AbstractContainerManager {

    private static final int MONGO_PORT = 27017;
    private static final int MAX_RETRIES = 10;

    private static final String[] PING_COMMAND = {"sh", "-c", "mongo --eval 'db.runCommand({ ping: 1 })' --quiet"};
    private static final String[] REPLICA_SET_COMMAND = {"sh", "-c", "mongo --username admin --password secret --authenticationDatabase admin --eval 'rs.initiate()' --quiet"};

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

    @SneakyThrows
    @Override
    protected void executeInContainer() {
        waitForMongoDBReady();

        log.info("Initializing MongoDB replica set... {}", REPLICA_SET_COMMAND);
        Container.ExecResult execResult = getInstance().execInContainer(REPLICA_SET_COMMAND);
        log.info("MongoDB replica set was Initialized successfully: {}", execResult.getStderr());

        TimeUnit.MILLISECONDS.sleep(500);
    }

    private void waitForMongoDBReady() throws IOException, InterruptedException {
        int retries = 1;
        while (!isPingSucessful() && retries <= MAX_RETRIES) {
            log.info("{} starting... {}/{}", createContainerName(), retries, MAX_RETRIES);
            TimeUnit.MILLISECONDS.sleep(100);
            retries++;
        }
    }

    private boolean isPingSucessful() throws IOException, InterruptedException {
        Container.ExecResult execResult = getInstance().execInContainer(PING_COMMAND);
        return execResult.getStdout().contains("\"ok\" : 1");
    }
}