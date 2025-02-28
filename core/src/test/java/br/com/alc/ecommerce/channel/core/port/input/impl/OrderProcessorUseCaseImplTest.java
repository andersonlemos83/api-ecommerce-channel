package br.com.alc.ecommerce.channel.core.port.input.impl;

import br.com.alc.ecommerce.channel.core.domain.order.*;
import br.com.alc.ecommerce.channel.core.port.output.MostRecentOrderFinderOutPort;
import br.com.alc.ecommerce.channel.core.port.output.OrderInserterOutPort;
import br.com.alc.ecommerce.channel.core.port.output.OrderInvoicerOutPort;
import br.com.alc.ecommerce.channel.core.service.watch.WatchService;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static br.com.alc.ecommerce.channel.core.domain.order.PaymentMethod.CREDIT;
import static br.com.alc.ecommerce.channel.core.domain.order.PaymentMethod.PIX;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SuppressWarnings("java:S5786") // Public required for JUnit test suite
@ExtendWith(SpringExtension.class)
public class OrderProcessorUseCaseImplTest {

    private static final String INVOICE_PENDING_ORDER_EXPECTED = "Order(id=null, orderRequest=OrderRequest(channelCode=APP, companyCode=001, storeCode=100, pos=105, orderNumber=987654322, totalValue=105.04, freightValue=5.05, customer=Customer(name=Martin Kauê Lopes, document=60778532402, documentType=CPF, address=Rua Projetada 913, addressNumber=622, addressComplement=Apt 202, neighborhood=Antares, city=Maceió, state=AL, country=Brasil, zipCode=57048434, phone=82992344475, email=martin_lopes@rafaelmarin.net), items=[ShoppingCartItem(code=100231933559, quantity=1, value=7.09), ShoppingCartItem(code=874631202305, quantity=2, value=17.68), ShoppingCartItem(code=392084657819, quantity=3, value=19.18)], payments=[Payment(paymentMethod=CREDIT, paymentDate=2025-01-30T13:45:01, authorizationCode=270606, cardNumber=3556777163651312, pixKey=null, value=100.0), Payment(paymentMethod=PIX, paymentDate=2025-01-30T13:45:01, authorizationCode=270607, cardNumber=null, pixKey=82992344475, value=5.04)]), invoiceKey=null, invoiceNumber=null, issuanceDate=null, invoiceBase64=null, status=INVOICE_PENDING, errorReason=null, createdDate=2025-01-30T13:45:01, updatedDate=2025-01-30T13:45:01)";
    private static final String ERRO_ORDER_EXPECTED = "Order(id=null, orderRequest=OrderRequest(channelCode=APP, companyCode=001, storeCode=100, pos=105, orderNumber=987654322, totalValue=105.04, freightValue=5.05, customer=Customer(name=Martin Kauê Lopes, document=60778532402, documentType=CPF, address=Rua Projetada 913, addressNumber=622, addressComplement=Apt 202, neighborhood=Antares, city=Maceió, state=AL, country=Brasil, zipCode=57048434, phone=82992344475, email=martin_lopes@rafaelmarin.net), items=[ShoppingCartItem(code=100231933559, quantity=1, value=7.09), ShoppingCartItem(code=874631202305, quantity=2, value=17.68), ShoppingCartItem(code=392084657819, quantity=3, value=19.18)], payments=[Payment(paymentMethod=CREDIT, paymentDate=2025-01-30T13:45:01, authorizationCode=270606, cardNumber=3556777163651312, pixKey=null, value=100.0), Payment(paymentMethod=PIX, paymentDate=2025-01-30T13:45:01, authorizationCode=270607, cardNumber=null, pixKey=82992344475, value=5.04)]), invoiceKey=null, invoiceNumber=null, issuanceDate=null, invoiceBase64=null, status=ERROR, errorReason=A nota fiscal não foi emitida., createdDate=2025-01-30T13:45:01, updatedDate=2025-01-30T13:45:01)";

    @InjectMocks
    private OrderProcessorUseCaseImpl orderProcessorUseCase;

    @Mock
    private MostRecentOrderFinderOutPort mostRecentOrderFinderOutPortMock;

    @Mock
    private OrderInserterOutPort orderInserterOutPortMock;

    @Mock
    private OrderInvoicerOutPort orderInvoicerOutPortMock;

    @Mock
    private WatchService watchServiceMock;

    @Test
    void givenAnNoExistingOrderAndAnInProcessingResponseWhenExecutingTheOrderProcessorThenShouldCallOrderInvoicerOutPortAndCallOrderInserterOutPort() {
        OrderRequest orderRequest = buildOrderRequest();
        LocalDateTime nowExpected = buildNow();
        OrderResponse orderResponse = Instancio.of(OrderResponse.class).set(Select.field("status"), SaleStatus.IN_PROCESSING).create();

        when(mostRecentOrderFinderOutPortMock.execute(orderRequest.getOrderNumber())).thenReturn(Optional.empty());
        when(orderInvoicerOutPortMock.execute(orderRequest)).thenReturn(orderResponse);
        when(watchServiceMock.nowLocalDateTime()).thenReturn(nowExpected);

        orderProcessorUseCase.execute(orderRequest);

        verify(orderInvoicerOutPortMock, times(1)).execute(orderRequest);
        ArgumentCaptor<Order> invoicePendingOrderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderInserterOutPortMock, times(2)).execute(invoicePendingOrderCaptor.capture());

        assertEquals(INVOICE_PENDING_ORDER_EXPECTED, invoicePendingOrderCaptor.getValue().toString());
    }

    @Test
    void givenAnNoExistingOrderAndAnErrorResponseWhenExecutingTheOrderProcessorThenShouldCallOrderInvoicerOutPortAndCallOrderInserterOutPort() {
        OrderRequest orderRequest = buildOrderRequest();
        LocalDateTime nowExpected = buildNow();
        OrderResponse orderResponse = Instancio.of(OrderResponse.class).set(Select.field("status"), SaleStatus.ERROR).create();

        when(mostRecentOrderFinderOutPortMock.execute(orderRequest.getOrderNumber())).thenReturn(Optional.empty());
        when(orderInvoicerOutPortMock.execute(orderRequest)).thenReturn(orderResponse);
        when(watchServiceMock.nowLocalDateTime()).thenReturn(nowExpected);

        orderProcessorUseCase.execute(orderRequest);

        verify(orderInvoicerOutPortMock, times(1)).execute(orderRequest);
        ArgumentCaptor<Order> errorOrderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderInserterOutPortMock, times(2)).execute(errorOrderCaptor.capture());

        assertEquals(ERRO_ORDER_EXPECTED, errorOrderCaptor.getValue().toString());
    }

    @Test
    void givenExistingInvoicedOrderWhenExecutingTheOrderProcessorThenShouldCallOrderInvoicerOutPortAndCallOrderInserterOutPort2() {
        OrderRequest orderRequest = buildOrderRequest();
        Order orderReturned = Instancio.of(Order.class).set(Select.field("status"), OrderStatus.INVOICED).create();

        when(mostRecentOrderFinderOutPortMock.execute(orderRequest.getOrderNumber())).thenReturn(Optional.ofNullable(orderReturned));

        orderProcessorUseCase.execute(orderRequest);

        verifyNoInteractions(orderInvoicerOutPortMock, orderInserterOutPortMock, watchServiceMock);
    }

    private OrderRequest buildOrderRequest() {
        Customer customer = buildCustomer();
        List<ShoppingCartItem> buildShoppingCartItemList = buildShoppingCartItemList();
        List<Payment> buildPaymentList = buildPaymentList();
        return OrderRequest.builder()
                .channelCode("APP")
                .companyCode("001")
                .storeCode("100")
                .pos(105)
                .orderNumber("987654322")
                .totalValue(BigDecimal.valueOf(105.04))
                .freightValue(BigDecimal.valueOf(5.05))
                .customer(customer)
                .items(buildShoppingCartItemList)
                .payments(buildPaymentList)
                .build();
    }

    private Customer buildCustomer() {
        return Customer.builder()
                .name("Martin Kauê Lopes")
                .document("60778532402")
                .documentType(DocumentType.CPF)
                .address("Rua Projetada 913")
                .addressNumber("622")
                .addressComplement("Apt 202")
                .neighborhood("Antares")
                .city("Maceió")
                .state("AL")
                .country("Brasil")
                .zipCode("57048434")
                .phone("82992344475")
                .email("martin_lopes@rafaelmarin.net")
                .build();
    }

    private List<ShoppingCartItem> buildShoppingCartItemList() {
        ShoppingCartItem shoppingCartItem100231933559 = buildShoppingCartItem100231933559();
        ShoppingCartItem shoppingCartItem874631202305 = buildShoppingCartItem874631202305();
        ShoppingCartItem shoppingCartItem392084657819 = buildShoppingCartItem392084657819();
        return Arrays.asList(shoppingCartItem100231933559, shoppingCartItem874631202305, shoppingCartItem392084657819);
    }

    private ShoppingCartItem buildShoppingCartItem100231933559() {
        return ShoppingCartItem.builder()
                .code(BigInteger.valueOf(100231933559L))
                .quantity(1)
                .value(BigDecimal.valueOf(7.09))
                .build();
    }

    private ShoppingCartItem buildShoppingCartItem874631202305() {
        return ShoppingCartItem.builder()
                .code(BigInteger.valueOf(874631202305L))
                .quantity(2)
                .value(BigDecimal.valueOf(17.68))
                .build();
    }

    private ShoppingCartItem buildShoppingCartItem392084657819() {
        return ShoppingCartItem.builder()
                .code(BigInteger.valueOf(392084657819L))
                .quantity(3)
                .value(BigDecimal.valueOf(19.18))
                .build();
    }

    private List<Payment> buildPaymentList() {
        Payment paymentCredit = buildPaymentCredit();
        Payment paymentPix = buildPaymentPix();
        return Arrays.asList(paymentCredit, paymentPix);
    }

    private Payment buildPaymentCredit() {
        return Payment.builder()
                .paymentMethod(CREDIT)
                .paymentDate(buildNow())
                .authorizationCode("270606")
                .cardNumber("3556777163651312")
                .pixKey(null)
                .value(BigDecimal.valueOf(100.00))
                .build();
    }

    private Payment buildPaymentPix() {
        return Payment.builder()
                .paymentMethod(PIX)
                .paymentDate(buildNow())
                .authorizationCode("270607")
                .cardNumber(null)
                .pixKey("82992344475")
                .value(BigDecimal.valueOf(5.04))
                .build();
    }

    private LocalDateTime buildNow() {
        return LocalDateTime.now()
                .withYear(2025)
                .withMonth(01)
                .withDayOfMonth(30)
                .withHour(13)
                .withMinute(45)
                .withSecond(01)
                .withNano(0);
    }
}