package br.com.alc.ecommerce.channel.infrastructure.config;

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
    void whenExecutingWatchServiceMethodThenShouldReturnAnInstanceOfRealWatchService() {
        BeanConfiguration beanConfiguration = new BeanConfiguration();
        WatchService watchService = beanConfiguration.watchService();
        assertTrue("", watchService instanceof RealWatchService);
    }
}