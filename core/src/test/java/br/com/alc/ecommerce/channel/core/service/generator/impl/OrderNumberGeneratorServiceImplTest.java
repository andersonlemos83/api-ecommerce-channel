package br.com.alc.ecommerce.channel.core.service.generator.impl;

import br.com.alc.ecommerce.channel.core.domain.bot.OrderBotRequest;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;

import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("java:S5786") // Public required for JUnit test suite
@ExtendWith(SpringExtension.class)
public class OrderNumberGeneratorServiceImplTest {

    @Test
    void givenAnOrderQuantityOfOneWhenExecutingTheOrderNumberGeneratorThenShouldReturnTheExpectedQuantityOfOrderNumbers() {
        int quantityExpected = 1;
        int quantityReturned = executeOrderNumberGenerator(quantityExpected);
        assertEquals(quantityExpected, quantityReturned, "Should return the expected quantity of order numbers");
    }

    @Test
    void givenAnOrderQuantityOfHundredWhenExecutingTheOrderNumberGeneratorThenShouldReturnTheExpectedQuantityOfOrderNumbers() {
        int quantityExpected = 100;
        int quantityReturned = executeOrderNumberGenerator(quantityExpected);
        assertEquals(quantityExpected, quantityReturned, "Should return the expected quantity of order numbers");
    }

    private int executeOrderNumberGenerator(int quantityExpected) {
        OrderBotRequest orderBotRequest = OrderBotRequest.builder().orderQuantity(quantityExpected).build();
        Flux<String> fluxReturned = new OrderNumberGeneratorServiceImpl().execute(orderBotRequest);
        return (int) fluxReturned.buffer(ofSeconds(2))
                .blockLast()
                .stream()
                .filter(StringUtils::isNumeric)
                .count();
    }
}