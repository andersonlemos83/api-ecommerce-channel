package br.com.alc.ecommerce.channel.core.port.input;

import br.com.alc.ecommerce.channel.core.port.input.impl.ByOrderNumberOrderFinderUseCaseImplTest;
import br.com.alc.ecommerce.channel.core.port.input.impl.ByPeriodOrderFinderUseCaseImplTest;
import br.com.alc.ecommerce.channel.core.port.input.impl.OrderGeneratorUseCaseImplTest;
import br.com.alc.ecommerce.channel.core.port.input.impl.OrderNumberGeneratorUseCaseImplTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SelectClasses({
        ByOrderNumberOrderFinderUseCaseImplTest.class,
        ByPeriodOrderFinderUseCaseImplTest.class,
        OrderGeneratorUseCaseImplTest.class,
        OrderNumberGeneratorUseCaseImplTest.class
})
@SuppressWarnings("squid:S2187")
@SuiteDisplayName("Suite that gathers all unit tests of the Input package.")
public class AllInputPortUnitTests {

}