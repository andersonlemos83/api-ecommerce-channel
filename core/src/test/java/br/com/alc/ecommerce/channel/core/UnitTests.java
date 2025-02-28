package br.com.alc.ecommerce.channel.core;

import br.com.alc.ecommerce.channel.core.port.input.AllInputPortUnitTests;
import br.com.alc.ecommerce.channel.core.service.AllServiceUnitTests;
import br.com.alc.ecommerce.channel.core.util.AllUtilUnitTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SelectClasses({
        AllInputPortUnitTests.class,
        AllServiceUnitTests.class,
        AllUtilUnitTests.class
})
@SuppressWarnings("squid:S2187")
@SuiteDisplayName("Suite that gathers all unit tests")
public class UnitTests {

}