package br.com.alc.ecommerce.channel.infrastructure.configuration;

import br.com.alc.ecommerce.channel.core.port.input.OrderGeneratorUseCase;
import br.com.alc.ecommerce.channel.core.port.input.OrderNumberGeneratorUseCase;
import br.com.alc.ecommerce.channel.core.port.input.impl.OrderGeneratorUseCaseImpl;
import br.com.alc.ecommerce.channel.core.port.input.impl.OrderNumberGeneratorUseCaseImpl;
import br.com.alc.ecommerce.channel.core.port.output.OrderIntegratorOutPort;
import br.com.alc.ecommerce.channel.core.port.output.OrderNumberIntegratorOutPort;
import br.com.alc.ecommerce.channel.core.service.generator.OrderNumberGeneratorService;
import br.com.alc.ecommerce.channel.core.service.generator.impl.OrderNumberGeneratorServiceImpl;
import br.com.alc.ecommerce.channel.infrastructure.EcommerceChannelInfrastructureApplication;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = EcommerceChannelInfrastructureApplication.class)
public class BeanConfiguration {

    @Bean
    public OrderNumberGeneratorUseCase orderNumberGeneratorUseCase(OrderNumberGeneratorService orderNumberGeneratorService,
                                                                   OrderNumberIntegratorOutPort orderNumberIntegratorOutPort) {
        return new OrderNumberGeneratorUseCaseImpl(orderNumberGeneratorService, orderNumberIntegratorOutPort);
    }

    @Bean
    public OrderNumberGeneratorService orderNumberService() {
        return new OrderNumberGeneratorServiceImpl();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public OrderGeneratorUseCase orderGeneratorUseCase(OrderIntegratorOutPort orderIntegratorOutPort) {
        return new OrderGeneratorUseCaseImpl(orderIntegratorOutPort);
    }
}