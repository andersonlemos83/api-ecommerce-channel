package br.com.alc.ecommerce.channel.core;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SelectClasses({
        UnitTests.class
})
@SuppressWarnings("squid:S2187")
@SuiteDisplayName("Suite that gathers all unit and acceptance tests")
public class AllTests {

}