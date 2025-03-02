package br.com.alc.ecommerce.channel.core.service.customerinvoice.impl;

import br.com.alc.ecommerce.channel.core.domain.customerinvoice.CustomerInvoiceRequest;
import br.com.alc.ecommerce.channel.core.domain.order.Order;
import br.com.alc.ecommerce.channel.core.port.output.CustomerInvoiceSenderOutPort;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SuppressWarnings("java:S5786") // Public required for JUnit test suite
@ExtendWith(SpringExtension.class)
public class CustomerInvoiceSenderServiceImplTest {

    private static final String EMAIL_BODY_EXPECTED = """
            <!DOCTYPE html>
            <html lang="pt-BR">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Nota Fiscal</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                    }
                    .container {
                        max-width: 600px;
                        margin: 0 auto;
                        padding: 20px;
                        border: 1px solid #ddd;
                        border-radius: 8px;
                        background-color: #f9f9f9;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h2>Nota Fiscal Eletrônica (NF-e)</h2>
                    <p><strong>E-COMMERCE DIGITAL FICTÍCIO LTDA</strong><br>CNPJ 63855524000184</p>
                    <p><strong>Total (R$):</strong> 105,04</p>
                    <p><strong>Emissão:</strong> 30/01/2025 13:48:00</p>
                    <p><strong>Descrição:</strong> Compra fictícia no E-COMMERCE DIGITAL FICTÍCIO LTDA</p>
                    <p><strong>Número da nota:</strong> 000000001</p>
                    <p><strong>Chave da nota:</strong> 27250212345678550010000000011234567898765432</p>
                </div>
            </body>
            </html>
            """;

    @InjectMocks
    private CustomerInvoiceSenderServiceImpl customerInvoiceSenderService;

    @Mock
    private CustomerInvoiceSenderOutPort customerInvoiceSenderOutPortMock;

    @Test
    void whenExecutingTheCustomerInvoiceSenderThenShouldCallCustomerInvoiceSenderOutPort() {
        customerInvoiceSenderService.execute(buildOrder());

        ArgumentCaptor<CustomerInvoiceRequest> captor = ArgumentCaptor.forClass(CustomerInvoiceRequest.class);
        verify(customerInvoiceSenderOutPortMock, times(1)).execute(captor.capture());

        CustomerInvoiceRequest request = captor.getValue();

        assertEquals("martin_lopes@rafaelmarin.net", request.getEmailTo());
        assertEquals("Email de Nota Fiscal - E-Commerce Digital Fictício Ltda", request.getEmailSubject());
        assertEquals(EMAIL_BODY_EXPECTED, request.getEmailBody());
        assertEquals("UklGRtzoBQBXRUJQVlA---TESTE---4IGwsBADQXwqdASoABAAEPjEW", request.getAttachmentBase64());
        assertEquals("nota-fiscal-27250212345678550010000000011234567898765432.png", request.getFileName());
    }

    private Order buildOrder() {
        Order order = Instancio.create(Order.class);
        order.getOrderRequest().setTotalValue(BigDecimal.valueOf(105.04));
        order.getOrderRequest().getCustomer().setEmail("martin_lopes@rafaelmarin.net");
        order.setInvoiceKey("27250212345678550010000000011234567898765432");
        order.setInvoiceNumber("000000001");
        order.setIssuanceDate(LocalDateTime.now()
                .withYear(2025)
                .withMonth(01)
                .withDayOfMonth(30)
                .withHour(13)
                .withMinute(48)
                .withSecond(00)
                .withNano(0));
        order.setInvoiceBase64("UklGRtzoBQBXRUJQVlA---TESTE---4IGwsBADQXwqdASoABAAEPjEW");
        return order;
    }
}