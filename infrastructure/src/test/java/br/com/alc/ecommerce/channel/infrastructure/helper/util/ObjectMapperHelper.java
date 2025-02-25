package br.com.alc.ecommerce.channel.infrastructure.helper.util;

import br.com.alc.ecommerce.channel.infrastructure.dto.error.ErrorResponseDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.test.web.servlet.ResultActions;

import java.io.InputStream;
import java.util.TimeZone;

import static com.fasterxml.jackson.databind.DeserializationFeature.*;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static java.nio.charset.StandardCharsets.UTF_8;

public final class ObjectMapperHelper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setTimeZone(TimeZone.getDefault());
        objectMapper.disable(WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(ACCEPT_FLOAT_AS_INT);
        objectMapper.disable(FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.disable(FAIL_ON_MISSING_CREATOR_PROPERTIES);
        objectMapper.enable(ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        objectMapper.registerModule(new JavaTimeModule());

        objectMapper.configure(USE_BIG_DECIMAL_FOR_FLOATS, true);
        objectMapper.setNodeFactory(new JsonNodeFactory(true));
    }

    private ObjectMapperHelper() {
    }

    public static ObjectMapper getInstance() {
        return objectMapper;
    }

    public static String generateJson(Object objeto) {
        try {
            return objectMapper.writeValueAsString(objeto);
        } catch (Exception exception) {
            return "";
        }
    }

    public static JsonNode generateJsonNode(InputStream inputStream) throws Exception {
        return objectMapper.readValue(inputStream, JsonNode.class);
    }

    public static ErrorResponseDto generateErrorResponseDto(ResultActions result) throws Exception {
        return objectMapper.readValue(result.andReturn().getResponse().getContentAsString(UTF_8), ErrorResponseDto.class);
    }
}