package br.com.alc.ecommerce.channel.infrastructure.cucumber;

import io.cucumber.junit.CucumberOptions;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@IncludeEngines("cucumber")
@SuppressWarnings("squid:S2187")
@SelectClasspathResource("features")
@SuiteDisplayName("Suite that gathers all Acceptance tests with Cucumber")
@CucumberOptions(plugin = {"pretty", "json:target/cucumber.json"}, features = "src/test/resources/features")
public class RunCucumberTest {

}