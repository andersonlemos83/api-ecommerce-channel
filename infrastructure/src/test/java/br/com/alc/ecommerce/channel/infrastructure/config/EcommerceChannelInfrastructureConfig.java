package br.com.alc.ecommerce.channel.infrastructure.config;

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
}