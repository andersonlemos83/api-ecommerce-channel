package br.com.alc.ecommerce.channel.core.port.input.impl;

import br.com.alc.ecommerce.channel.core.domain.finder.OrderFinderRequest;
import br.com.alc.ecommerce.channel.core.domain.order.Order;
import br.com.alc.ecommerce.channel.core.port.output.ByPeriodOrderFinderFinderOutPort;
import br.com.alc.ecommerce.channel.core.service.validator.ByPeriodOrderFinderValidatorService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SuppressWarnings("java:S5786") // Public required for JUnit test suite
@ExtendWith(SpringExtension.class)
public class ByPeriodOrderFinderUseCaseImplTest {

    @InjectMocks
    private ByPeriodOrderFinderUseCaseImpl byPeriodOrderFinderUseCase;

    @Mock
    private ByPeriodOrderFinderValidatorService byPeriodOrderFinderValidatorServiceMock;

    @Mock
    private ByPeriodOrderFinderFinderOutPort byPeriodOrderFinderFinderOutPortMock;

    @Test
    void whenExecutingTheByPeriodOrderFinderThenShouldCallByPeriodOrderFinderValidatorServiceAndCallByPeriodOrderFinderFinderOutPort() {
        OrderFinderRequest orderFinderRequest = Instancio.create(OrderFinderRequest.class);
        Order orderExpected = Instancio.create(Order.class);
        when(byPeriodOrderFinderValidatorServiceMock.validate(orderFinderRequest)).thenReturn(Mono.empty());
        when(byPeriodOrderFinderFinderOutPortMock.execute(orderFinderRequest)).thenReturn(Flux.just(orderExpected));

        Order orderReturned = byPeriodOrderFinderUseCase.execute(orderFinderRequest).blockLast();

        verify(byPeriodOrderFinderValidatorServiceMock, times(1)).validate(orderFinderRequest);
        verify(byPeriodOrderFinderFinderOutPortMock, times(1)).execute(orderFinderRequest);
        assertEquals(orderExpected, orderReturned);
    }
}