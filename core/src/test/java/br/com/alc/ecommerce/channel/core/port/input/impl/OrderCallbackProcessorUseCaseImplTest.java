package br.com.alc.ecommerce.channel.core.port.input.impl;

import br.com.alc.ecommerce.channel.core.domain.callback.OrderCallbackRequest;
import br.com.alc.ecommerce.channel.core.domain.order.Order;
import br.com.alc.ecommerce.channel.core.domain.order.OrderStatus;
import br.com.alc.ecommerce.channel.core.domain.order.SaleStatus;
import br.com.alc.ecommerce.channel.core.port.output.MostRecentOrderFinderOutPort;
import br.com.alc.ecommerce.channel.core.port.output.OrderInserterOutPort;
import br.com.alc.ecommerce.channel.core.service.customerinvoice.CustomerInvoiceSenderService;
import br.com.alc.ecommerce.channel.core.service.watch.WatchService;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SuppressWarnings("java:S5786") // Public required for JUnit test suite
@ExtendWith(SpringExtension.class)
public class OrderCallbackProcessorUseCaseImplTest {

    @InjectMocks
    private OrderCallbackProcessorUseCaseImpl orderCallbackProcessorUseCase;

    @Mock
    private MostRecentOrderFinderOutPort mostRecentOrderFinderOutPortMock;

    @Mock
    private CustomerInvoiceSenderService customerInvoiceSenderServiceMock;

    @Mock
    private OrderInserterOutPort orderInserterOutPortMock;

    @Mock
    private WatchService watchServiceMock;

    @Test
    void givenAnExistingOrderAndProcessedOrderCallbackWhenExecutingTheOrderCallbackProcessorThenShouldCallOrderInserterOutPortAndCallCustomerInvoiceSenderService() {
        OrderCallbackRequest orderCallbackRequest = Instancio.of(OrderCallbackRequest.class).set(Select.field("status"), SaleStatus.PROCESSED).create();
        Order orderReturned = Instancio.of(Order.class).set(Select.field("status"), OrderStatus.INVOICE_PENDING).create();
        LocalDateTime nowExpected = Instancio.create(LocalDateTime.class);
        Order orderExpected = buildOrderExpected(orderCallbackRequest, orderReturned, OrderStatus.INVOICED, nowExpected);

        when(mostRecentOrderFinderOutPortMock.execute(orderCallbackRequest.getOrderNumber())).thenReturn(Optional.ofNullable(orderReturned));
        when(watchServiceMock.nowLocalDateTime()).thenReturn(nowExpected);

        orderCallbackProcessorUseCase.execute(orderCallbackRequest);

        verify(orderInserterOutPortMock, times(1)).execute(orderExpected);
        verify(customerInvoiceSenderServiceMock, times(1)).execute(orderExpected);
    }

    @Test
    void givenAnExistingOrderAndErrorOrderCallbackWhenExecutingTheOrderCallbackProcessorThenShouldCallOrderInserterOutPort() {
        OrderCallbackRequest orderCallbackRequest = Instancio.of(OrderCallbackRequest.class).set(Select.field("status"), SaleStatus.ERROR).create();
        Order orderReturned = Instancio.of(Order.class).set(Select.field("status"), OrderStatus.INVOICE_PENDING).create();
        LocalDateTime nowExpected = Instancio.create(LocalDateTime.class);
        Order orderExpected = buildOrderExpected(orderCallbackRequest, orderReturned, OrderStatus.ERROR, nowExpected);

        when(mostRecentOrderFinderOutPortMock.execute(orderCallbackRequest.getOrderNumber())).thenReturn(Optional.ofNullable(orderReturned));
        when(watchServiceMock.nowLocalDateTime()).thenReturn(nowExpected);

        orderCallbackProcessorUseCase.execute(orderCallbackRequest);

        verify(orderInserterOutPortMock, times(1)).execute(orderExpected);
        verifyNoInteractions(customerInvoiceSenderServiceMock);
    }

    @Test
    void givenAnExistingOrderAndInProcessingOrderCallbackWhenExecutingTheOrderCallbackProcessorThenNoShouldCallOrderInserterOutPort() {
        OrderCallbackRequest orderCallbackRequest = Instancio.of(OrderCallbackRequest.class).set(Select.field("status"), SaleStatus.IN_PROCESSING).create();
        Order orderReturned = Instancio.of(Order.class).set(Select.field("status"), OrderStatus.INVOICE_PENDING).create();

        when(mostRecentOrderFinderOutPortMock.execute(orderCallbackRequest.getOrderNumber())).thenReturn(Optional.ofNullable(orderReturned));

        orderCallbackProcessorUseCase.execute(orderCallbackRequest);

        verifyNoInteractions(orderInserterOutPortMock, customerInvoiceSenderServiceMock, watchServiceMock);
    }

    @Test
    void givenAnNoExistingOrderAndProcessedOrderCallbackCallbackWhenExecutingTheOrderCallbackProcessorThenNoShouldCallOrderInserterOutPort() {
        OrderCallbackRequest orderCallbackRequest = Instancio.of(OrderCallbackRequest.class).set(Select.field("status"), SaleStatus.PROCESSED).create();

        when(mostRecentOrderFinderOutPortMock.execute(orderCallbackRequest.getOrderNumber())).thenReturn(Optional.empty());

        orderCallbackProcessorUseCase.execute(orderCallbackRequest);

        verifyNoInteractions(orderInserterOutPortMock, customerInvoiceSenderServiceMock, watchServiceMock);
    }

    private Order buildOrderExpected(OrderCallbackRequest orderCallbackRequest, Order orderReturned, OrderStatus orderStatus, LocalDateTime nowExpected) {
        return Order.builder()
                .id(orderReturned.getId())
                .orderRequest(orderReturned.getOrderRequest())
                .invoiceKey(orderCallbackRequest.getInvoiceKey())
                .invoiceNumber(orderCallbackRequest.getInvoiceNumber())
                .issuanceDate(orderCallbackRequest.getIssuanceDate())
                .invoiceBase64(orderCallbackRequest.getInvoiceBase64())
                .status(orderStatus)
                .errorReason(orderCallbackRequest.getErrorReason())
                .createdDate(orderReturned.getCreatedDate())
                .updatedDate(nowExpected)
                .build();
    }
}