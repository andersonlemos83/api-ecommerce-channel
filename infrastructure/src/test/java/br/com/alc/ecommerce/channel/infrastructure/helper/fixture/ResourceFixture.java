package br.com.alc.ecommerce.channel.infrastructure.helper.fixture;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

import static br.com.alc.ecommerce.channel.infrastructure.helper.util.ObjectMapperHelper.generateJson;
import static br.com.alc.ecommerce.channel.infrastructure.helper.util.ObjectMapperHelper.generateJsonNode;
import static java.nio.charset.StandardCharsets.UTF_8;

public class ResourceFixture {

    private ResourceFixture() {
    }

    public static String getContentFromResource(String resourceName) {
        try {
            InputStream stream = ResourceFixture.class.getResourceAsStream(resourceName);
            return StreamUtils.copyToString(stream, UTF_8);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static String getContentFromResourceJson(String resourceName) {
        try (InputStream inputStream = ResourceFixture.class.getResourceAsStream(resourceName)) {
            if (inputStream == null || inputStream.available() == 0) {
                return null;
            }
            JsonNode jsonNode = generateJsonNode(inputStream);
            String json = generateJson(jsonNode);
            if (!SystemUtils.OS_NAME.contains("Windows")) {
                json = json.replace("\\r\\n", "\\n");
            }
            return json;
        } catch (Exception exception) {
            throw new RuntimeException(buildMessage(resourceName), exception);
        }
    }

    private static String buildMessage(String resourceName) {
        return MessageFormat.format("The file {0} was not found or is invalid!", resourceName);
    }
}