package br.com.alc.ecommerce.channel.infrastructure.configuration;

import feign.Target;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.CircuitBreakerNameResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.lang.reflect.Method;

@Log4j2
@Configuration
public class FeignConfiguration {

    @Bean
    public HttpMessageConverters customConverters() {
        return new HttpMessageConverters(new MappingJackson2HttpMessageConverter());
    }

    @Bean
    public CircuitBreakerNameResolver circuitBreakerNameResolver() {
        return (String feignClientName, Target<?> target, Method method) -> {
            String circuitBreakerName = feignClientName + "_" + method.getName();
            log.info("Circuit breaker name: {}", circuitBreakerName);
            return circuitBreakerName;
        };
    }
}