package br.com.alc.ecommerce.channel.infrastructure.helper.util;

import br.com.alc.ecommerce.channel.core.domain.order.OrderRequest;
import br.com.alc.ecommerce.channel.infrastructure.dto.order.OrderRequestDto;
import br.com.alc.ecommerce.channel.infrastructure.persistence.document.OrderDocument;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.util.TimeZone;

import static com.fasterxml.jackson.databind.DeserializationFeature.*;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

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

    @SneakyThrows
    public static OrderRequest generateOrderRequest(String json) {
        return objectMapper.readValue(json, OrderRequest.class);
    }

    @SneakyThrows
    public static OrderRequestDto generateOrderRequestDto(String json) {
        return objectMapper.readValue(json, OrderRequestDto.class);
    }

    @SneakyThrows
    public static OrderDocument generateOrderDocument(String json) {
        return objectMapper.readValue(json, OrderDocument.class);
    }
}