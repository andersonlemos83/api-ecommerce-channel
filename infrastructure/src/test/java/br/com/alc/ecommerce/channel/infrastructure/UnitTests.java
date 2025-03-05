package br.com.alc.ecommerce.channel.infrastructure;

import br.com.alc.ecommerce.channel.infrastructure.config.BeanConfigurationTest;
import br.com.alc.ecommerce.channel.infrastructure.email.impl.CustomerInvoiceEmailSenderImplTest;
import br.com.alc.ecommerce.channel.infrastructure.messaging.producer.impl.MessagingProducerImplTest;
import br.com.alc.ecommerce.channel.infrastructure.web.controller.OrderControllerTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SelectClasses({
        BeanConfigurationTest.class,
        CustomerInvoiceEmailSenderImplTest.class,
        MessagingProducerImplTest.class,
        OrderControllerTest.class
})
@SuppressWarnings("squid:S2187")
@SuiteDisplayName("Suite that gathers all Unit tests")
public class UnitTests {

}