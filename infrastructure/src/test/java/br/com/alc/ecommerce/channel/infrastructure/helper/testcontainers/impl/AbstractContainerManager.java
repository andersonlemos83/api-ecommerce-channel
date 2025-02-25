package br.com.alc.ecommerce.channel.infrastructure.helper.testcontainers.impl;

import br.com.alc.ecommerce.channel.infrastructure.helper.testcontainers.ContainerManager;
import jakarta.annotation.PreDestroy;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.testcontainers.containers.GenericContainer;

import java.util.concurrent.TimeUnit;

@Log4j2
@SuppressWarnings("squid:S2925") // "Thread.sleep" should not be used in tests
public abstract class AbstractContainerManager implements ContainerManager {

    private final GenericContainer container;

    public AbstractContainerManager() {
        this.container = createContainer();
    }

    protected abstract GenericContainer createContainer();

    protected abstract String createContainerName();

    @Override
    public void start() {
        if (!getInstance().isRunning()) {
            try {
                log.info("{} starting...", createContainerName());
                getInstance().start();
                log.info("{} started successfully!", createContainerName());
                executeInContainer();
            } catch (Exception exception) {
                log.error("Error starting the {}: {}", createContainerName(), exception.getMessage(), exception);
                stop();
            }
        }
    }

    @PreDestroy
    @Override
    public void stop() {
        log.info("{} shutting down...", createContainerName());
        getInstance().stop();
        log.info("{} shut down successfully!", createContainerName());
    }

    @SneakyThrows
    @Override
    public void restart() {
        while (getInstance().isRunning()) {
            stop();
            TimeUnit.MILLISECONDS.sleep(100);
        }
        start();
    }

    @Override
    public boolean isRunning() {
        return getInstance().isRunning();
    }

    protected GenericContainer getInstance() {
        return container;
    }

    protected void executeInContainer() {
        log.info("No extra command executed in the {}", createContainerName());
    }
}