package br.com.alc.ecommerce.channel.infrastructure.config;

import br.com.alc.ecommerce.channel.core.port.output.MostRecentOrderFinderOutPort;
import br.com.alc.ecommerce.channel.core.service.generator.OrderGeneratorService;
import br.com.alc.ecommerce.channel.core.service.generator.ZipCodeGeneratorService;
import br.com.alc.ecommerce.channel.core.service.generator.stub.OrderGeneratorServiceStub;
import br.com.alc.ecommerce.channel.core.service.generator.stub.ZipCodeGeneratorServiceStub;
import br.com.alc.ecommerce.channel.core.service.watch.WatchService;
import br.com.alc.ecommerce.channel.core.service.watch.stub.VirtualWatchService;
import br.com.alc.ecommerce.channel.infrastructure.adapter.output.stub.MostRecentOrderFinderOutPortStub;
import br.com.alc.ecommerce.channel.infrastructure.persistence.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Properties;

@Configuration
public class EcommerceChannelInfrastructureConfig {

    @Primary
    @Bean("watchService")
    public WatchService watchService() {
        return new VirtualWatchService();
    }

    @Primary
    @Bean("cepGeneratorService")
    public ZipCodeGeneratorService cepGeneratorService() {
        return new ZipCodeGeneratorServiceStub();
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

    @Primary
    @Bean("gitProperties")
    public GitProperties gitProperties() {
        Properties properties = new Properties();
        properties.put("branch", "test");
        properties.put("commit.id", "c4f908dbfef2699ef5d024301d3eb579b12198ea");
        properties.put("commit.id.abbrev", "c4f908d");
        properties.put("commit.time", "2025-03-03T19\\:18\\:33-0300");
        properties.put("build.time", "2025-03-03T19\\:18\\:33-0300");
        return new GitProperties(properties);
    }

    @Primary
    @Bean("buildProperties")
    public BuildProperties buildProperties() {
        Properties properties = new Properties();
        properties.put("group", "br.com.alc");
        properties.put("artifact", "api-ecommerce-channel-infrastructure");
        properties.put("name", "api-ecommerce-channel-infrastructure");
        properties.put("version", "1.0.0-TEST");
        properties.put("time", "2025-03-03T19\\:18\\:33-0300");
        return new BuildProperties(properties);
    }
}