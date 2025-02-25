package br.com.alc.ecommerce.channel.infrastructure.cucumber;

import br.com.alc.ecommerce.channel.infrastructure.EcommerceChannelInfrastructureApplication;
import br.com.alc.ecommerce.channel.infrastructure.config.EcommerceChannelInfrastructureConfig;
import br.com.alc.ecommerce.channel.infrastructure.config.RabbitConfig;
import br.com.alc.ecommerce.channel.infrastructure.config.WireMockConfig;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.StepDefs;
import br.com.alc.ecommerce.channel.infrastructure.helper.testcontainers.ContainerManager;
import br.com.alc.ecommerce.channel.infrastructure.helper.testcontainers.factory.impl.ContainerFactoryImpl;
import br.com.alc.ecommerce.channel.infrastructure.helper.util.ObjectMapperHelper;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.*;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

@WebAppConfiguration
@ActiveProfiles("test")
@CucumberContextConfiguration
@AutoConfigureMockMvc(printOnlyOnFailure = true) // Set false for debug
@SpringBootTest(classes = EcommerceChannelInfrastructureApplication.class)
@ContextConfiguration(classes = {EcommerceChannelInfrastructureApplication.class, EcommerceChannelInfrastructureConfig.class, RabbitConfig.class, WireMockConfig.class})
public class SpringContextStepDefs extends StepDefs {

    @DefaultParameterTransformer
    @DefaultDataTableEntryTransformer
    @DefaultDataTableCellTransformer
    public Object defaultTransformer(Object value, Type type) {
        final ObjectMapper objectMapper = ObjectMapperHelper.getInstance().copy();
        final Object handledValue = handleEmptyValues(value);
        final JavaType javaType = objectMapper.constructType(type);
        return objectMapper.convertValue(handledValue, javaType);
    }

    private Object handleEmptyValues(Object value) {
        if (value instanceof Map) {
            Map handledMap = new LinkedHashMap();
            for (Object o : ((Map) value).entrySet()) {
                Map.Entry entry = (Map.Entry) o;
                Object handledValue = handleEmptyValue(entry.getValue());
                entry.setValue(handledValue);
                handledMap.put(entry.getKey(), entry.getValue());
            }
            return handledMap;
        }

        return handleEmptyValue(value);
    }

    private Object handleEmptyValue(Object o) {
        if (o instanceof String && "<empty>".equalsIgnoreCase((String) o)) {
            return "";
        }
        return o;
    }

    @Before
    @Override
    public void initializeContext() {
        super.initializeContext();
    }

    @BeforeAll
    public static void initializeContainers() {
        new ContainerFactoryImpl().getInstances().forEach(ContainerManager::start);
    }

    @AfterAll
    public static void finalizeContainers() {
        new ContainerFactoryImpl().getInstances().forEach(ContainerManager::stop);
    }
}