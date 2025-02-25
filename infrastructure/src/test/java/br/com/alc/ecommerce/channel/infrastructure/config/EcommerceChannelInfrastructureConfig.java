package br.com.alc.ecommerce.channel.infrastructure.config;

import br.com.alc.ecommerce.channel.core.service.generator.CepGeneratorService;
import br.com.alc.ecommerce.channel.core.service.generator.OrderGeneratorService;
import br.com.alc.ecommerce.channel.core.service.generator.stub.CepGeneratorServiceStub;
import br.com.alc.ecommerce.channel.core.service.generator.stub.OrderGeneratorServiceStub;
import br.com.alc.ecommerce.channel.core.service.watch.WatchService;
import br.com.alc.ecommerce.channel.core.service.watch.stub.VirtualWatchService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class EcommerceChannelInfrastructureConfig {

    @Primary
    @Bean("watchService")
    public WatchService watchService() {
        return new VirtualWatchService();
    }

    @Primary
    @Bean("cepGeneratorService")
    public CepGeneratorService cepGeneratorService() {
        return new CepGeneratorServiceStub();
    }

    @Primary
    @Bean("orderGeneratorService")
    public OrderGeneratorService orderGeneratorService() {
        return new OrderGeneratorServiceStub();
    }
}