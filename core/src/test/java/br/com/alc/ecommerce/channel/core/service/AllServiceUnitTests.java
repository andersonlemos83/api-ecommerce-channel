package br.com.alc.ecommerce.channel.core.service;

import br.com.alc.ecommerce.channel.core.service.watch.impl.RealWatchServiceTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SelectClasses({
        RealWatchServiceTest.class
})
@SuppressWarnings("squid:S2187")
@SuiteDisplayName("Suite that gathers all unit tests of the Service package")
public class AllServiceUnitTests {

}