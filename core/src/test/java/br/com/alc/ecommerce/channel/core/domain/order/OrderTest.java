package br.com.alc.ecommerce.channel.core.domain.order;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("java:S5786") // Public required for JUnit test suite
@ExtendWith(SpringExtension.class)
public class OrderTest {

    @Test
    void whenExecutingTheFetchOrderNumberMethodThenShouldReturnTheExpedtedOrderNumber() {
        Order order = Instancio.create(Order.class);
        String orderNumberReturned = order.fetchOrderNumber();
        assertEquals(order.getOrderRequest().getOrderNumber(), orderNumberReturned);
    }
}