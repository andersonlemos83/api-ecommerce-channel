package br.com.alc.ecommerce.channel.infrastructure;

import br.com.alc.ecommerce.channel.infrastructure.config.StaticContextTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SelectClasses({
        StaticContextTest.class
})
@SuppressWarnings("squid:S2187")
@SuiteDisplayName("Suite used to initialize static context")
public class BeforeTests {

}