package br.com.alc.ecommerce.channel.core.port.input.impl;

import br.com.alc.ecommerce.channel.core.domain.address.AddressResponse;
import br.com.alc.ecommerce.channel.core.domain.generator.OrderGeneratorRequest;
import br.com.alc.ecommerce.channel.core.domain.order.OrderRequest;
import br.com.alc.ecommerce.channel.core.port.output.AddressFinderOutPort;
import br.com.alc.ecommerce.channel.core.port.output.OrderIntegratorOutPort;
import br.com.alc.ecommerce.channel.core.service.generator.OrderGeneratorService;
import br.com.alc.ecommerce.channel.core.service.generator.ZipCodeGeneratorService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;

@SuppressWarnings("java:S5786") // Public required for JUnit test suite
@ExtendWith(SpringExtension.class)
public class OrderGeneratorUseCaseImplTest {

    @InjectMocks
    private OrderGeneratorUseCaseImpl orderGeneratorUseCase;

    @Mock
    private ZipCodeGeneratorService zipCodeGeneratorServiceMock;

    @Mock
    private AddressFinderOutPort addressFinderOutPortMock;

    @Mock
    private OrderGeneratorService orderGeneratorServiceMock;

    @Mock
    private OrderIntegratorOutPort orderIntegratorOutPortMock;

    @Test
    void whenExecutingTheOrderGeneratorThenShouldCallOrderIntegratorOutPort() {
        OrderGeneratorRequest orderGeneratorRequest = Instancio.create(OrderGeneratorRequest.class);
        String zipCodeExpected = Instancio.create(String.class);
        AddressResponse addressResponseExpected = Instancio.create(AddressResponse.class);
        OrderRequest orderRequestExpected = Instancio.create(OrderRequest.class);
        when(zipCodeGeneratorServiceMock.execute()).thenReturn(zipCodeExpected);
        when(addressFinderOutPortMock.execute(zipCodeExpected)).thenReturn(addressResponseExpected);
        when(orderGeneratorServiceMock.execute(orderGeneratorRequest, addressResponseExpected)).thenReturn(orderRequestExpected);

        orderGeneratorUseCase.execute(orderGeneratorRequest);

        verify(orderIntegratorOutPortMock, times(1)).execute(orderRequestExpected);
    }
}
