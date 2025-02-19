package br.com.alc.ecommerce.channel.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.log4j.Log4j2;

import java.util.TimeZone;

import static com.fasterxml.jackson.databind.DeserializationFeature.*;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

@Log4j2
public final class ObjectMapperUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setTimeZone(TimeZone.getDefault());
        objectMapper.disable(WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(ACCEPT_FLOAT_AS_INT);
        objectMapper.disable(FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.disable(FAIL_ON_MISSING_CREATOR_PROPERTIES);
        objectMapper.enable(ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        objectMapper.registerModule(new JavaTimeModule());
    }

    private ObjectMapperUtil() {
    }

    public static ObjectMapper getInstance() {
        return objectMapper;
    }

    public static String generateJson(Object object) {
        try {
            return getInstance().writeValueAsString(object);
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            return object.toString();
        }
    }
}