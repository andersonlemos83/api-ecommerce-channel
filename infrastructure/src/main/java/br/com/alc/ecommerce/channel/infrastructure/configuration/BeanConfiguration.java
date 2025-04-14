package br.com.alc.ecommerce.channel.infrastructure.configuration;

import br.com.alc.ecommerce.channel.core.port.input.*;
import br.com.alc.ecommerce.channel.core.port.input.impl.*;
import br.com.alc.ecommerce.channel.core.port.output.*;
import br.com.alc.ecommerce.channel.core.service.customerinvoice.CustomerInvoiceSenderService;
import br.com.alc.ecommerce.channel.core.service.customerinvoice.impl.CustomerInvoiceSenderServiceImpl;
import br.com.alc.ecommerce.channel.core.service.generator.OrderGeneratorService;
import br.com.alc.ecommerce.channel.core.service.generator.OrderNumberGeneratorService;
import br.com.alc.ecommerce.channel.core.service.generator.ZipCodeGeneratorService;
import br.com.alc.ecommerce.channel.core.service.generator.impl.OrderGeneratorServiceImpl;
import br.com.alc.ecommerce.channel.core.service.generator.impl.OrderNumberGeneratorServiceImpl;
import br.com.alc.ecommerce.channel.core.service.generator.impl.ZipCodeGeneratorServiceImpl;
import br.com.alc.ecommerce.channel.core.service.validator.ByPeriodOrderFinderValidatorService;
import br.com.alc.ecommerce.channel.core.service.validator.impl.ByPeriodOrderFinderValidatorServiceImpl;
import br.com.alc.ecommerce.channel.core.service.watch.WatchService;
import br.com.alc.ecommerce.channel.core.service.watch.impl.RealWatchService;
import br.com.alc.ecommerce.channel.infrastructure.EcommerceChannelInfrastructureApplication;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import static org.modelmapper.convention.MatchingStrategies.LOOSE;

@Configuration
@ComponentScan(basePackageClasses = EcommerceChannelInfrastructureApplication.class)
public class BeanConfiguration {

    @Lazy
    @Bean
    public OrderNumberGeneratorUseCase orderNumberGeneratorUseCase(OrderNumberGeneratorService orderNumberGeneratorService,
                                                                   OrderNumberIntegratorOutPort orderNumberIntegratorOutPort) {
        return new OrderNumberGeneratorUseCaseImpl(orderNumberGeneratorService, orderNumberIntegratorOutPort);
    }

    @Lazy
    @Bean
    public OrderNumberGeneratorService orderNumberService() {
        return new OrderNumberGeneratorServiceImpl();
    }

    @Lazy
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Lazy
    @Bean
    public ModelMapper looseModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(LOOSE);
        return modelMapper;
    }

    @Lazy
    @Bean
    public OrderGeneratorUseCase orderGeneratorUseCase(ZipCodeGeneratorService zipCodeGeneratorService,
                                                       AddressFinderOutPort addressFinderOutPort,
                                                       OrderGeneratorService orderGeneratorService,
                                                       OrderIntegratorOutPort orderIntegratorOutPort) {
        return new OrderGeneratorUseCaseImpl(zipCodeGeneratorService, addressFinderOutPort, orderGeneratorService, orderIntegratorOutPort);
    }

    @Lazy
    @Bean
    public ZipCodeGeneratorService zipCodeGeneratorService() {
        return new ZipCodeGeneratorServiceImpl();
    }

    @Lazy
    @Bean
    public OrderGeneratorService orderGeneratorService(WatchService watchService) {
        return new OrderGeneratorServiceImpl(watchService);
    }

    @Lazy
    @Bean
    public WatchService watchService() {
        return new RealWatchService();
    }

    @Lazy
    @Bean
    public OrderProcessorUseCase orderProcessorUseCase(MostRecentOrderFinderOutPort mostRecentOrderFinderOutPort,
                                                       OrderInserterOutPort orderInserterOutPort,
                                                       OrderInvoicerOutPort orderInvoicerOutPort,
                                                       WatchService watchService) {
        return new OrderProcessorUseCaseImpl(mostRecentOrderFinderOutPort, orderInserterOutPort, orderInvoicerOutPort, watchService);
    }

    @Lazy
    @Bean
    public OrderCallbackProcessorUseCase orderCallbackProcessorUseCase(MostRecentOrderFinderOutPort mostRecentOrderFinderOutPort,
                                                                       OrderInserterOutPort orderInserterOutPort,
                                                                       CustomerInvoiceSenderService customerInvoiceSenderService,
                                                                       WatchService watchService) {
        return new OrderCallbackProcessorUseCaseImpl(mostRecentOrderFinderOutPort, orderInserterOutPort, customerInvoiceSenderService, watchService);
    }

    @Lazy
    @Bean
    public ByOrderNumberOrderFinderUseCase byOrderNumberOrderFinderUseCase(ByOrderNumberOrderFinderOutPort byOrderNumberOrderFinderOutPort) {
        return new ByOrderNumberOrderFinderUseCaseImpl(byOrderNumberOrderFinderOutPort);
    }

    @Lazy
    @Bean
    public ByPeriodOrderFinderUseCase byPeriodOrderFinderUseCase(ByPeriodOrderFinderValidatorService byPeriodOrderFinderValidatorService,
                                                                 ByPeriodOrderFinderFinderOutPort byPeriodOrderFinderFinderOutPort) {
        return new ByPeriodOrderFinderUseCaseImpl(byPeriodOrderFinderValidatorService, byPeriodOrderFinderFinderOutPort);
    }

    @Lazy
    @Bean
    public ByPeriodOrderFinderValidatorService byPeriodOrderFinderValidatorService() {
        return new ByPeriodOrderFinderValidatorServiceImpl();
    }

    @Lazy
    @Bean
    public CustomerInvoiceSenderService customerInvoiceSenderService(CustomerInvoiceSenderOutPort customerInvoiceSenderOutPort, @Value("${email.from}") String emailFrom) {
        return new CustomerInvoiceSenderServiceImpl(customerInvoiceSenderOutPort, emailFrom);
    }
}