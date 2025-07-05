package br.com.alc.ecommerce.channel.infrastructure.cucumber;

import org.junit.platform.suite.api.*;

import static io.cucumber.junit.platform.engine.Constants.FEATURES_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SuppressWarnings("squid:S2187")
@ConfigurationParametersResource("cucumber.properties")
@SelectPackages("br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs")
@SuiteDisplayName("Suite that gathers all Acceptance tests with Cucumber")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "br.com.alc.ecommerce.channel.infrastructure.cucumber")
@ConfigurationParameter(key = FEATURES_PROPERTY_NAME, value = "src/test/resources/features")
public class RunCucumberTest {

}