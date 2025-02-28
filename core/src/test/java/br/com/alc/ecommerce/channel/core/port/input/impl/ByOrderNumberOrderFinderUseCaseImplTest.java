package br.com.alc.ecommerce.channel.core.port.input.impl;

import br.com.alc.ecommerce.channel.core.domain.order.Order;
import br.com.alc.ecommerce.channel.core.exception.OrderNotFoundException;
import br.com.alc.ecommerce.channel.core.port.output.ByOrderNumberOrderFinderOutPort;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SuppressWarnings("java:S5786") // Public required for JUnit test suite
@ExtendWith(SpringExtension.class)
public class ByOrderNumberOrderFinderUseCaseImplTest {

    @InjectMocks
    private ByOrderNumberOrderFinderUseCaseImpl byOrderNumberOrderFinderUseCase;

    @Mock
    private ByOrderNumberOrderFinderOutPort byOrderNumberOrderFinderOutPortMock;

    @Test
    void givenAnExistingOrderWhenExecutingTheByOrderNumberOrderFinderThenShouldCallByOrderNumberOrderFinderOutPortAndReturnTheExpectedOrder() {
        String orderNumber = "123456";
        Order orderExpected = Instancio.create(Order.class);
        when(byOrderNumberOrderFinderOutPortMock.execute(orderNumber)).thenReturn(Mono.just(orderExpected));

        Order orderReturned = byOrderNumberOrderFinderUseCase.execute(orderNumber).block();

        verify(byOrderNumberOrderFinderOutPortMock, times(1)).execute(orderNumber);
        assertEquals(orderExpected, orderReturned);
    }

    @Test
    void givenAnNoExistingOrderWhenExecutingTheByOrderNumberOrderFinderThenShouldThrowsAnOrderNotFoundException() {
        String orderNumber = "123456";
        when(byOrderNumberOrderFinderOutPortMock.execute(orderNumber)).thenReturn(Mono.empty());

        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class, () -> byOrderNumberOrderFinderUseCase.execute(orderNumber).block());

        verify(byOrderNumberOrderFinderOutPortMock, times(1)).execute(orderNumber);
        assertEquals("O pedido não foi encontrado.", exception.getMessage());
    }
}