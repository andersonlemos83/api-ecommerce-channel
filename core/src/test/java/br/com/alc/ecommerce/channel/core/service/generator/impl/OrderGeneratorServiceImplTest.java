package br.com.alc.ecommerce.channel.core.service.generator.impl;

import br.com.alc.ecommerce.channel.core.domain.address.AddressResponse;
import br.com.alc.ecommerce.channel.core.domain.generator.OrderGeneratorRequest;
import br.com.alc.ecommerce.channel.core.domain.order.*;
import br.com.alc.ecommerce.channel.core.service.watch.WatchService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static br.com.alc.ecommerce.channel.core.domain.order.PaymentMethod.*;
import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SuppressWarnings("java:S5786") // Public required for JUnit test suite
@ExtendWith(SpringExtension.class)
public class OrderGeneratorServiceImplTest {

    @InjectMocks
    private OrderGeneratorServiceImpl orderGeneratorService;

    @Mock
    private WatchService watchServiceMock;

    @Test
    void whenExecutingTheOrderGeneratorThenShouldReturnTheExpectedRandomOrderRequest() {
        OrderGeneratorRequest orderGeneratorRequest = buildOrderGeneratorRequest();
        AddressResponse addressResponse = buildAddressResponse();

        LocalDateTime nowExpected = buildNow();
        when(watchServiceMock.nowLocalDateTime()).thenReturn(nowExpected);

        for (int i = 0; i < 10; i++) {
            OrderRequest orderRequest = orderGeneratorService.execute(orderGeneratorRequest, addressResponse);
            assertOrderRequest(orderRequest, orderGeneratorRequest, addressResponse, nowExpected);
        }
    }

    private void assertOrderRequest(OrderRequest orderRequest, OrderGeneratorRequest orderGeneratorRequest, AddressResponse addressResponse, LocalDateTime nowExpected) {
        assertTrue(Arrays.asList("WEB", "APP", "STR", "SLF").contains(orderRequest.getChannelCode()));
        assertTrue(StringUtils.isNumeric(orderRequest.getCompanyCode()));
        assertEquals(3, StringUtils.length(orderRequest.getCompanyCode()));
        assertTrue(StringUtils.isNumeric(orderRequest.getStoreCode()));
        assertEquals(3, StringUtils.length(orderRequest.getStoreCode()));
        assertNotNull(orderRequest.getPos());
        assertEquals(orderGeneratorRequest.getOrderNumber(), orderRequest.getOrderNumber());

        assertCustomer(orderRequest, addressResponse);
        assertItems(orderRequest);
        assertPayments(orderRequest, nowExpected);

        assertNotNull(orderRequest.getTotalValue());
        assertNotNull(orderRequest.getFreightValue());
        assertEquals(orderRequest.getTotalValue(), orderRequest.getPayments().stream().map(Payment::getValue).reduce(ZERO, BigDecimal::add));
        assertEquals(orderRequest.getTotalValue().subtract(orderRequest.getFreightValue()), orderRequest.getItems().stream().map(ShoppingCartItem::generateTotalItemValue).reduce(ZERO, BigDecimal::add));
    }

    private OrderGeneratorRequest buildOrderGeneratorRequest() {
        return OrderGeneratorRequest.builder()
                .orderNumber("987654322")
                .build();
    }

    private AddressResponse buildAddressResponse() {
        return AddressResponse.builder()
                .zipCode("57048-434")
                .street("Rua Projetada 913")
                .complement("Apt 202")
                .neighborhood("Antares")
                .city("Maceió")
                .state("AL")
                .ddd("82")
                .erro(false)
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

    private void assertItems(OrderRequest orderRequest) {
        assertNotNull(orderRequest.getItems());
        orderRequest.getItems().forEach(this::assertItem);
    }

    private void assertItem(ShoppingCartItem shoppingCartItem) {
        assertNotNull(shoppingCartItem.getCode());
        assertNotNull(shoppingCartItem.getQuantity());
        assertNotNull(shoppingCartItem.getValue());
    }

    private void assertCustomer(OrderRequest orderRequest, AddressResponse addressResponse) {
        assertNotNull(orderRequest.getCustomer());
        assertNotNull(orderRequest.getCustomer().getName());
        assertNotNull(orderRequest.getCustomer().getDocument());
        assertTrue(isValidDocument(orderRequest));
        assertTrue(Arrays.asList(DocumentType.values()).contains(orderRequest.getCustomer().getDocumentType()));
        assertEquals(addressResponse.getStreet(), orderRequest.getCustomer().getAddress());
        assertNotNull(orderRequest.getCustomer().getAddressNumber());
        assertTrue(StringUtils.isNumeric(orderRequest.getCustomer().getAddressNumber()));
        assertEquals(addressResponse.getComplement(), orderRequest.getCustomer().getAddressComplement());
        assertEquals(addressResponse.getNeighborhood(), orderRequest.getCustomer().getNeighborhood());
        assertEquals(addressResponse.getCity(), orderRequest.getCustomer().getCity());
        assertEquals(addressResponse.getState(), orderRequest.getCustomer().getState());
        assertEquals("Brasil", orderRequest.getCustomer().getCountry());
        assertEquals(addressResponse.getZipCode(), orderRequest.getCustomer().getZipCode());
        assertNotNull(orderRequest.getCustomer().getPhone());
        assertTrue(orderRequest.getCustomer().getPhone().contains(addressResponse.getDdd()));
        assertEquals(15, StringUtils.length(orderRequest.getCustomer().getPhone()));
        assertNotNull(orderRequest.getCustomer().getEmail());
        assertTrue(orderRequest.getCustomer().getEmail().contains("@gmail.com"));
    }

    private boolean isValidDocument(OrderRequest orderRequest) {
        return StringUtils.length(orderRequest.getCustomer().getDocument()) == 14 || StringUtils.length(orderRequest.getCustomer().getDocument()) == 18;
    }

    private void assertPayments(OrderRequest orderRequest, LocalDateTime nowExpected) {
        assertNotNull(orderRequest.getPayments());
        assertEquals(1, orderRequest.getPayments().size());

        Payment payment = orderRequest.getPayments().stream().findFirst().get();

        assertTrue(Arrays.asList(PaymentMethod.values()).contains(payment.getPaymentMethod()));
        assertEquals(nowExpected, payment.getPaymentDate());
        assertTrue(StringUtils.isNumeric(payment.getAuthorizationCode()));
        assertTrue(isValidCardNumber(payment));
        assertTrue(isValidPixKey(payment));
        assertNotNull(payment.getValue());
    }

    private boolean isValidCardNumber(Payment payment) {
        if (CREDIT.equals(payment.getPaymentMethod()) || DEBIT.equals(payment.getPaymentMethod())) {
            return StringUtils.isNumeric(payment.getCardNumber());
        }
        return payment.getCardNumber() == null;
    }

    private boolean isValidPixKey(Payment payment) {
        if (PIX.equals(payment.getPaymentMethod())) {
            return payment.getPixKey() != null && payment.getPixKey().length() == 36;
        }
        return payment.getPixKey() == null;
    }
}