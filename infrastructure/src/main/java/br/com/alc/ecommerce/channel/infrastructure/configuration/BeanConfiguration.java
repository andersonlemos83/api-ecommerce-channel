package br.com.alc.ecommerce.channel.infrastructure.configuration;

import br.com.alc.ecommerce.channel.core.port.input.OrderNumberGeneratorUseCase;
import br.com.alc.ecommerce.channel.core.port.input.impl.OrderNumberGeneratorUseCaseImpl;
import br.com.alc.ecommerce.channel.core.port.output.OrderNumberIntegratorOutPort;
import br.com.alc.ecommerce.channel.core.service.OrderNumberService;
import br.com.alc.ecommerce.channel.core.service.impl.OrderNumberServiceImpl;
import br.com.alc.ecommerce.channel.infrastructure.EcommerceChannelInfrastructureApplication;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = EcommerceChannelInfrastructureApplication.class)
public class BeanConfiguration {

    @Bean
    public OrderNumberGeneratorUseCase orderNumberGeneratorUseCase(OrderNumberService orderNumberService,
                                                                   OrderNumberIntegratorOutPort orderNumberIntegratorOutPort) {
        return new OrderNumberGeneratorUseCaseImpl(orderNumberService, orderNumberIntegratorOutPort);
    }

    @Bean
    public OrderNumberService orderNumberService() {
        return new OrderNumberServiceImpl();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}