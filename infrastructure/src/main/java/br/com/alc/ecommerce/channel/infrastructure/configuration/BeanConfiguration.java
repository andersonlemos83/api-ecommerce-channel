package br.com.alc.ecommerce.channel.infrastructure.configuration;

import br.com.alc.ecommerce.channel.core.port.input.*;
import br.com.alc.ecommerce.channel.core.port.input.impl.*;
import br.com.alc.ecommerce.channel.core.port.output.*;
import br.com.alc.ecommerce.channel.core.service.generator.CepGeneratorService;
import br.com.alc.ecommerce.channel.core.service.generator.OrderGeneratorService;
import br.com.alc.ecommerce.channel.core.service.generator.OrderNumberGeneratorService;
import br.com.alc.ecommerce.channel.core.service.generator.impl.CepGeneratorServiceImpl;
import br.com.alc.ecommerce.channel.core.service.generator.impl.OrderGeneratorServiceImpl;
import br.com.alc.ecommerce.channel.core.service.generator.impl.OrderNumberGeneratorServiceImpl;
import br.com.alc.ecommerce.channel.core.service.watch.WatchService;
import br.com.alc.ecommerce.channel.core.service.watch.impl.RealWatchService;
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
    public OrderGeneratorUseCase orderGeneratorUseCase(CepGeneratorService cepGeneratorService,
                                                       AddressFinderOutPort addressFinderOutPort,
                                                       OrderGeneratorService orderGeneratorService,
                                                       OrderIntegratorOutPort orderIntegratorOutPort) {
        return new OrderGeneratorUseCaseImpl(cepGeneratorService, addressFinderOutPort, orderGeneratorService, orderIntegratorOutPort);
    }

    @Bean
    public CepGeneratorService cepGeneratorService() {
        return new CepGeneratorServiceImpl();
    }

    @Bean
    public OrderGeneratorService orderGeneratorService(WatchService watchService) {
        return new OrderGeneratorServiceImpl(watchService);
    }

    @Bean
    public WatchService watchService() {
        return new RealWatchService();
    }

    @Bean
    public OrderProcessorUseCase orderProcessorUseCase(MostRecentOrderFinderOutPort mostRecentOrderFinderOutPort,
                                                       OrderInserterOutPort orderInserterOutPort,
                                                       OrderInvoicerOutPort orderInvoicerOutPort,
                                                       WatchService watchService) {
        return new OrderProcessorUseCaseImpl(mostRecentOrderFinderOutPort, orderInserterOutPort, orderInvoicerOutPort, watchService);
    }

    @Bean
    public OrderCallbackProcessorUseCase orderCallbackProcessorUseCase(MostRecentOrderFinderOutPort mostRecentOrderFinderOutPort,
                                                                       OrderInserterOutPort orderInserterOutPort,
                                                                       WatchService watchService) {
        return new OrderCallbackProcessorUseCaseImpl(mostRecentOrderFinderOutPort, orderInserterOutPort, watchService);
    }

    @Bean
    public ByOrderNumberOrderFinderUseCase byOrderNumberOrderFinderUseCase(ByOrderNumberOrderFinderOutPort byOrderNumberOrderFinderOutPort) {
        return new ByOrderNumberOrderFinderUseCaseImpl(byOrderNumberOrderFinderOutPort);
    }

    @Bean
    public ByPeriodOrderFinderUseCase byPeriodOrderFinderUseCase() {
        return new ByPeriodOrderFinderUseCaseImpl();
    }
}