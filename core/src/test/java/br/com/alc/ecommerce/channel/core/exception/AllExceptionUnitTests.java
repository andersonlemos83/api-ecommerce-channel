package br.com.alc.ecommerce.channel.core.exception;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SelectClasses({
        DefaultOutPortExceptionTest.class,
        ZipCodeNotFoundExceptionTest.class
})
@SuppressWarnings("squid:S2187")
@SuiteDisplayName("Suite that gathers all unit tests of the Exception package")
public class AllExceptionUnitTests {

}