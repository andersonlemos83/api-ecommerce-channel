package br.com.alc.ecommerce.channel.infrastructure.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static br.com.alc.ecommerce.channel.infrastructure.util.ConstantesUtil.ADDRESS_FINDER_CACHE;

@EnableCaching
@Configuration
public class CacheConfiguration {

    private static final RedisSerializationContext.SerializationPair<Object> SERIALIZATION_CONTEXT = RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json());

    @Bean
    public CacheManager cacheManager(final RedisConnectionFactory redisConnectionFactory, final ResourceLoader resourceLoader) {
        final Map<String, RedisCacheConfiguration> caches = new HashMap<>();

        caches.put(ADDRESS_FINDER_CACHE, RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .serializeValuesWith(SERIALIZATION_CONTEXT)
                .entryTtl(Duration.ofMinutes(5L)));

        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory)
                .withInitialCacheConfigurations(caches)
                .build();
    }
}