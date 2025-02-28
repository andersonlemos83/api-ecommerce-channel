package br.com.alc.ecommerce.channel.core.port.input.impl;

import br.com.alc.ecommerce.channel.core.domain.bot.OrderBotRequest;
import br.com.alc.ecommerce.channel.core.domain.bot.OrderBotResponse;
import br.com.alc.ecommerce.channel.core.domain.generator.OrderGeneratorRequest;
import br.com.alc.ecommerce.channel.core.port.output.OrderNumberIntegratorOutPort;
import br.com.alc.ecommerce.channel.core.service.generator.OrderNumberGeneratorService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SuppressWarnings("java:S5786") // Public required for JUnit test suite
@ExtendWith(SpringExtension.class)
public class OrderNumberGeneratorUseCaseImplTest {

    @InjectMocks
    private OrderNumberGeneratorUseCaseImpl orderNumberGeneratorUseCase;

    @Mock
    private OrderNumberGeneratorService orderNumberGeneratorServiceMock;

    @Mock
    private OrderNumberIntegratorOutPort orderNumberIntegratorOutPortMock;

    @Test
    void whenExecutingTheOrderNumberGeneratorThenShouldCallOrderNumberGeneratorServiceAndCallOrderNumberIntegratorOutPort() {
        OrderBotRequest orderBotRequest = Instancio.create(OrderBotRequest.class);
        String orderNumberExpected = Instancio.create(String.class);
        OrderGeneratorRequest orderGeneratorRequest = OrderGeneratorRequest.builder().orderNumber(orderNumberExpected).build();

        when(orderNumberGeneratorServiceMock.execute(orderBotRequest)).thenReturn(Flux.just(orderNumberExpected));
        when(orderNumberIntegratorOutPortMock.execute(orderGeneratorRequest)).thenReturn(Flux.just(orderNumberExpected));

        OrderBotResponse returned = orderNumberGeneratorUseCase.execute(orderBotRequest).blockLast();

        verify(orderNumberGeneratorServiceMock, times(1)).execute(orderBotRequest);
        verify(orderNumberIntegratorOutPortMock, times(1)).execute(orderGeneratorRequest);
        assertNotNull(returned);
        assertEquals(orderNumberExpected, returned.getOrderNumber());
    }
}