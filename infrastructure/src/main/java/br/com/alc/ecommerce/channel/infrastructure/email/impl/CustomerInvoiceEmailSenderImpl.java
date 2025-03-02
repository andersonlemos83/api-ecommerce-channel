package br.com.alc.ecommerce.channel.infrastructure.email.impl;

import br.com.alc.ecommerce.channel.infrastructure.dto.customerinvoice.CustomerInvoiceRequestDto;
import br.com.alc.ecommerce.channel.infrastructure.email.CustomerInvoiceEmailSender;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Base64;

import static br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil.generateJson;
import static org.apache.commons.lang3.exception.ExceptionUtils.getMessage;

@Log4j2
@Component
@AllArgsConstructor
public class CustomerInvoiceEmailSenderImpl implements CustomerInvoiceEmailSender {

    private static final String EMAIL_FROM = "no-reply@gmail.com";

    private final JavaMailSender javaMailSender;

    @Async
    @Override
    public void send(CustomerInvoiceRequestDto customerInvoiceRequestDto) {
        try {
            log.debug("Incoming into CustomerInvoiceEmailSenderImpl: {}", generateJson(customerInvoiceRequestDto));
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(customerInvoiceRequestDto.getEmailTo());
            mimeMessageHelper.setSubject(customerInvoiceRequestDto.getEmailSubject());
            mimeMessageHelper.setText(customerInvoiceRequestDto.getEmailBody(), true);
            mimeMessageHelper.setFrom(EMAIL_FROM);

            InputStreamSource attachmentInputStreamSource = buildAttachmentInputStreamSource(customerInvoiceRequestDto.getAttachmentBase64());
            mimeMessageHelper.addAttachment(customerInvoiceRequestDto.getFileName(), attachmentInputStreamSource);

            log.info("---> Sending e-mail to {}", mimeMessage.getAllRecipients());
            javaMailSender.send(mimeMessage);
        } catch (Exception exception) {
            log.error("Error in the CustomerInvoiceEmailSenderImpl: {}", getMessage(exception), exception);
        }
    }

    private InputStreamSource buildAttachmentInputStreamSource(String attachmentBase64) {
        byte[] decodedBytes = Base64.getDecoder().decode(attachmentBase64);
        return new ByteArrayResource(decodedBytes);
    }
}