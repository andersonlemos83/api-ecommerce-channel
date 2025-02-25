package br.com.alc.ecommerce.channel.infrastructure.helper.testcontainers.impl;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.testcontainers.containers.Container;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.RabbitMQContainer;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@Log4j2
@SuppressWarnings("squid:S2925") // "Thread.sleep" should not be used in tests
public class ContainerManagerRabbitMQ extends AbstractContainerManager {

    private static final int SERVICE_PORT = 5672;
    private static final int MANAGEMENT_PORT = 15672;

    private static final List<String> VHOST_ENABLE_PLUGINS_COMMAND = asList("rabbitmq-plugins", "enable", "rabbitmq_management", "rabbitmq_management_agent", "rabbitmq_shovel");
    private static final List<String> VHOST_ECOMMERCE_CHECKOUT_COMMAND = asList("rabbitmqadmin", "declare", "vhost", "name=ecommerce-checkout");
    private static final List<String> USER_ECOMMERCE_CHECKOUT_COMMAND = asList("rabbitmqadmin", "declare", "user", "name=ecommerce-checkout", "password=ecommerce-checkout", "tags=" + String.join(",", singletonList("administrator")));
    private static final List<String> PERMISSION_ECOMMERCE_CHECKOUT_TO_ECOMMERCE_CHECKOUT_COMMAND = asList("rabbitmqadmin", "declare", "permission", "vhost=ecommerce-checkout", "user=ecommerce-checkout", "configure=.*", "write=.*", "read=.*");

    private static final List<List<String>> COMMANDS = asList(VHOST_ENABLE_PLUGINS_COMMAND, VHOST_ECOMMERCE_CHECKOUT_COMMAND, USER_ECOMMERCE_CHECKOUT_COMMAND, PERMISSION_ECOMMERCE_CHECKOUT_TO_ECOMMERCE_CHECKOUT_COMMAND);

    @Override
    protected GenericContainer createContainer() {
        RabbitMQContainer container = new RabbitMQContainer("rabbitmq:3.7.25-management-alpine");
        container.setPortBindings(asList(SERVICE_PORT + ":" + SERVICE_PORT, MANAGEMENT_PORT + ":" + MANAGEMENT_PORT));
        container.withExposedPorts(SERVICE_PORT, MANAGEMENT_PORT);
        return container;
    }

    @Override
    protected String createContainerName() {
        return "RabbitMQ";
    }

    @SneakyThrows
    @Override
    protected void executeInContainer() {
        while (!getInstance().isRunning()) {
            TimeUnit.MILLISECONDS.sleep(100);
        }

        COMMANDS.forEach(cmd -> {
            try {
                Container.ExecResult execResult = getInstance().execInContainer(cmd.toArray(new String[0]));
                if (execResult.getExitCode() != 0) {
                    log.error("The command {} was not executed successfully: {}", cmd, execResult.getStderr());
                    return;
                }
                log.info("The command {} was executed successfully: {}", cmd, execResult.getStderr());
            } catch (InterruptedException | IOException exception) {
                log.error("The command {} was not executed successfully: {}", cmd, exception.getMessage());
            }
        });
    }
}