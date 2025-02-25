package br.com.alc.ecommerce.channel.infrastructure.helper.manager.impl;

import br.com.alc.ecommerce.channel.infrastructure.helper.manager.RedisManager;
import br.com.alc.ecommerce.channel.infrastructure.helper.testcontainers.factory.impl.ContainerFactoryImpl;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

@Log4j2
@Component
@AllArgsConstructor
public class RedisManagerImpl implements RedisManager {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void clearCache() {
        boolean isRunning = new ContainerFactoryImpl().getRedisInstance().isRunning();
        if (isRunning) {
            Set<String> keys = redisTemplate.keys("*");
            keys.forEach(redisTemplate::delete);
            log.info("Redis keys deleted successfully: {}", keys);
        }
    }
}