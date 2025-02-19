package br.com.alc.ecommerce.channel.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class RetryConfiguration {

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setBackOffPolicy(gerarFixedBackOffPolicy());
        retryTemplate.setRetryPolicy(gerarSimpleRetryPolicy());
        return retryTemplate;
    }

    private FixedBackOffPolicy gerarFixedBackOffPolicy() {
        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(1000L);
        return fixedBackOffPolicy;
    }

    private SimpleRetryPolicy gerarSimpleRetryPolicy() {
        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
        simpleRetryPolicy.setMaxAttempts(3);
        return simpleRetryPolicy;
    }
}