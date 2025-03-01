package br.com.alc.ecommerce.channel.infrastructure.cucumber.configurator;

import br.com.alc.ecommerce.channel.infrastructure.helper.manager.MongoDbManager;
import br.com.alc.ecommerce.channel.infrastructure.helper.manager.RabbitMqManager;
import br.com.alc.ecommerce.channel.infrastructure.helper.manager.RedisManager;
import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Arrays.asList;

@Log4j2
@Component
@AllArgsConstructor
public class EnvironmentConfigurator {

    private static final List<String> QUEUES = asList("order-generator-queue", "order-queue", "sale-callback-queue");

    private final MongoDbManager mongoDbManager;
    private final RabbitMqManager rabbitMqManager;
    private final RedisManager redisManager;
    private final WireMockServer wireMockServer;

    public void configureEnvironment() {
        log.info("START - Initializing Context");
        mongoDbManager.cleanDatabase();
        rabbitMqManager.disableAllListeners();
        rabbitMqManager.clearQueues(QUEUES);
        redisManager.clearCache();
        wireMockServer.resetAll();
        log.info("END - Initializing Context");
    }
}