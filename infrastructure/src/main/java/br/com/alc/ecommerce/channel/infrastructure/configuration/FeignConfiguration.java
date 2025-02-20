package br.com.alc.ecommerce.channel.infrastructure.configuration;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class FeignConfiguration {

    @Bean
    public HttpMessageConverters customConverters() {
        return new HttpMessageConverters(new MappingJackson2HttpMessageConverter());
    }
}