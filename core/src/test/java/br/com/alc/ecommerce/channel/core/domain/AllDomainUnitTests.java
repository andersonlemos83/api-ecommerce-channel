package br.com.alc.ecommerce.channel.core.domain;

import br.com.alc.ecommerce.channel.core.domain.order.OrderTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SelectClasses({
        OrderTest.class
})
@SuppressWarnings("squid:S2187")
@SuiteDisplayName("Suite that gathers all unit tests of the Domain package")
public class AllDomainUnitTests {

}