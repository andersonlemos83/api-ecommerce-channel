package br.com.alc.ecommerce.channel.infrastructure.config;

import br.com.alc.ecommerce.channel.core.service.generator.OrderGeneratorService;
import br.com.alc.ecommerce.channel.core.service.generator.ZipCodeGeneratorService;
import br.com.alc.ecommerce.channel.core.service.generator.impl.OrderGeneratorServiceImpl;
import br.com.alc.ecommerce.channel.core.service.generator.impl.ZipCodeGeneratorServiceImpl;
import br.com.alc.ecommerce.channel.core.service.watch.WatchService;
import br.com.alc.ecommerce.channel.core.service.watch.impl.RealWatchService;
import br.com.alc.ecommerce.channel.infrastructure.configuration.BeanConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.assertTrue;

@SuppressWarnings("java:S5786") // Public required for JUnit test suite
@ExtendWith(SpringExtension.class)
public class BeanConfigurationTest {

    @Test
    void whenExecutingZipCodeGeneratorServiceMethodThenShouldReturnAnInstanceOfZipCodeGeneratorServiceImpl() {
        BeanConfiguration beanConfiguration = new BeanConfiguration();
        ZipCodeGeneratorService zipCodeGeneratorService = beanConfiguration.zipCodeGeneratorService();
        assertTrue("Should return an instance of ZipCodeGeneratorServiceImpl", zipCodeGeneratorService instanceof ZipCodeGeneratorServiceImpl);
    }

    @Test
    void whenExecutingOrderGeneratorServiceMethodThenShouldReturnAnInstanceOfOrderGeneratorServiceImpl() {
        BeanConfiguration beanConfiguration = new BeanConfiguration();
        OrderGeneratorService orderGeneratorService = beanConfiguration.orderGeneratorService(beanConfiguration.watchService());
        assertTrue("Should return an instance of OrderGeneratorServiceImpl", orderGeneratorService instanceof OrderGeneratorServiceImpl);
    }

    @Test
    void whenExecutingWatchServiceMethodThenShouldReturnAnInstanceOfRealWatchService() {
        BeanConfiguration beanConfiguration = new BeanConfiguration();
        WatchService watchService = beanConfiguration.watchService();
        assertTrue("Should return an instance of RealWatchService", watchService instanceof RealWatchService);
    }
}