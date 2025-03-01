package br.com.alc.ecommerce.channel.core.util;

import br.com.alc.ecommerce.channel.core.domain.bot.OrderBotRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("java:S5786") // Public required for JUnit test suite
@ExtendWith(SpringExtension.class)
public class ObjectMapperUtilTest {

    @Test
    void givenAnSerializableObjectWhenExecutingTheGenerateJsonMethodThenShouldReturnAnSerializedObject() {
        OrderBotRequest orderBotRequest = OrderBotRequest.builder().orderQuantity(100).build();
        String jsonReturned = ObjectMapperUtil.generateJson(orderBotRequest);
        assertEquals("{\"orderQuantity\":100}", jsonReturned);
    }

    @Test
    void givenAnNoSerializableObjectWhenExecutingTheGenerateJsonMethodThenShouldReturnAnNonSerializedObject() {
        Object nonSerializableObject = new Object();
        String jsonReturned = ObjectMapperUtil.generateJson(nonSerializableObject);
        assertEquals(nonSerializableObject.toString(), jsonReturned);
    }

    @Test
    void givenAnValidJsonWhenExecutingTheGenerateMapMethodThenShouldReturnAnExpectedMap() {
        Map<String, Object> mapExpected = new HashMap<>();
        mapExpected.put("orderQuantity", 100);

        Map<String, Object> mapReturned = ObjectMapperUtil.generateMap("{\"orderQuantity\":100}");

        assertEquals(mapExpected, mapReturned);
    }

    @Test
    void givenAnInvalidJsonWhenExecutingTheGenerateMapMethodThenShouldReturnAnEmptyMap() {
        Map<String, Object> mapExpected = new HashMap<>();
        Map<String, Object> mapReturned = ObjectMapperUtil.generateMap("");
        assertEquals(mapExpected, mapReturned);
    }
}