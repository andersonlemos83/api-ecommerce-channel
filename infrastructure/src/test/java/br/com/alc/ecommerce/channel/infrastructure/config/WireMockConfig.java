package br.com.alc.ecommerce.channel.infrastructure.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WireMockConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public WireMockServer wireMockServer() {
        return new WireMockServer(9090);
    }
}