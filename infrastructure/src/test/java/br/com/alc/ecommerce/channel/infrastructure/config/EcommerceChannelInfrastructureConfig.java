package br.com.alc.ecommerce.channel.infrastructure.config;

import br.com.alc.ecommerce.channel.core.port.output.MostRecentOrderFinderOutPort;
import br.com.alc.ecommerce.channel.core.service.generator.CepGeneratorService;
import br.com.alc.ecommerce.channel.core.service.generator.OrderGeneratorService;
import br.com.alc.ecommerce.channel.core.service.generator.stub.CepGeneratorServiceStub;
import br.com.alc.ecommerce.channel.core.service.generator.stub.OrderGeneratorServiceStub;
import br.com.alc.ecommerce.channel.core.service.watch.WatchService;
import br.com.alc.ecommerce.channel.core.service.watch.stub.VirtualWatchService;
import br.com.alc.ecommerce.channel.infrastructure.adapter.output.stub.MostRecentOrderFinderOutPortStub;
import br.com.alc.ecommerce.channel.infrastructure.persistence.repository.OrderRepository;
import org.modelmapper.ModelMapper;
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

    @Primary
    @Bean("mostRecentOrderFinderOutPort")
    public MostRecentOrderFinderOutPort mostRecentOrderFinderOutPort(OrderRepository orderRepository,
                                                                     ModelMapper modelMapper) {
        return new MostRecentOrderFinderOutPortStub(orderRepository, modelMapper);
    }
}