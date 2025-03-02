package br.com.alc.ecommerce.channel.infrastructure.email.impl;

import br.com.alc.ecommerce.channel.infrastructure.dto.customerinvoice.CustomerInvoiceRequestDto;
import br.com.alc.ecommerce.channel.infrastructure.helper.fixture.ResourceFixture;
import jakarta.mail.internet.MimeMessage;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;

@SuppressWarnings("java:S5786") // Public required for JUnit test suite
@ExtendWith(SpringExtension.class)
public class CustomerInvoiceEmailSenderImplTest {

    @InjectMocks
    private CustomerInvoiceEmailSenderImpl customerInvoiceEmailSender;

    @Mock
    private JavaMailSender javaMailSenderMock;

    @Test
    void givenAhValidCustomerInvoiceRequestWhenExecutingTheSendMethodThenShouldCallJavaMailSender() {
        String invoiceBase64 = ResourceFixture.getContentFromResource("/fixtures/InvoiceBase64-27250212345678550010000000011234567898765432.txt");
        CustomerInvoiceRequestDto customerInvoiceRequestDto = Instancio.create(CustomerInvoiceRequestDto.class);
        customerInvoiceRequestDto.setAttachmentBase64(invoiceBase64);
        MimeMessage mimeMessage = Instancio.create(MimeMessage.class);

        when(javaMailSenderMock.createMimeMessage()).thenReturn(mimeMessage);

        customerInvoiceEmailSender.send(customerInvoiceRequestDto);

        verify(javaMailSenderMock, times(1)).send(any(MimeMessage.class));
    }

    @Test
    void givenThatItThrowsAnyExceptionWhenExecutingTheSendMethodThenShouldGenerateAnErrorLog() {
        CustomerInvoiceRequestDto customerInvoiceRequestDto = Instancio.create(CustomerInvoiceRequestDto.class);

        doThrow(RuntimeException.class).when(javaMailSenderMock).createMimeMessage();

        customerInvoiceEmailSender.send(customerInvoiceRequestDto);

        verify(javaMailSenderMock, times(0)).send(any(MimeMessage.class));
    }
}