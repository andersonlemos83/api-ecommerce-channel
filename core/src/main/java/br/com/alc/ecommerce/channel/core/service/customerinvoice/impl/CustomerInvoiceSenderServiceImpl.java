package br.com.alc.ecommerce.channel.core.service.customerinvoice.impl;

import br.com.alc.ecommerce.channel.core.domain.customerinvoice.CustomerInvoiceRequest;
import br.com.alc.ecommerce.channel.core.domain.order.Order;
import br.com.alc.ecommerce.channel.core.port.output.CustomerInvoiceSenderOutPort;
import br.com.alc.ecommerce.channel.core.service.customerinvoice.CustomerInvoiceSenderService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

import static br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil.generateJson;

@Log4j2
@AllArgsConstructor
public class CustomerInvoiceSenderServiceImpl implements CustomerInvoiceSenderService {

    private static final String EMAIL_SUBJECT = "Email de Nota Fiscal - E-Commerce Digital Fictício Ltda";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final NumberFormat REAL_FORMATTER = NumberFormat.getInstance(new Locale("pt", "BR"));
    private static final String EMAIL_BODY_TEMPLATE = """
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
                    <p><strong>Total (R$):</strong> {total_value}</p>
                    <p><strong>Emissão:</strong> {issuance_date}</p>
                    <p><strong>Descrição:</strong> Compra fictícia no E-COMMERCE DIGITAL FICTÍCIO LTDA</p>
                    <p><strong>Número da nota:</strong> {invoice_number}</p>
                    <p><strong>Chave da nota:</strong> {invoice_key}</p>
                </div>
            </body>
            </html>
            """;
    private static final String FILE_NAME_TEMPLATE = "nota-fiscal-{0}.png";

    private final CustomerInvoiceSenderOutPort customerInvoiceSenderOutPort;
    private final String emailFrom;

    @Override
    public void execute(Order order) {
        log.info("Incoming into CustomerInvoiceSenderServiceImpl: {}", generateJson(order));
        CustomerInvoiceRequest customerInvoiceRequest = getCustomerInvoiceRequest(order);
        customerInvoiceSenderOutPort.execute(customerInvoiceRequest);
    }

    private CustomerInvoiceRequest getCustomerInvoiceRequest(Order order) {
        return CustomerInvoiceRequest.builder()
                .emailTo(order.fetchCustomerEmail())
                .emailFrom(emailFrom)
                .emailSubject(EMAIL_SUBJECT)
                .emailBody(buildEmailBody(order))
                .attachmentBase64(order.getInvoiceBase64())
                .fileName(buildFileName(order))
                .build();
    }

    private String buildEmailBody(Order order) {
        return EMAIL_BODY_TEMPLATE.replace("{total_value}", formatTotalValue(order.fetchTotalValue()))
                .replace("{issuance_date}", formatIssuanceDate(order.getIssuanceDate()))
                .replace("{invoice_number}", order.getInvoiceNumber())
                .replace("{invoice_key}", order.getInvoiceKey());
    }

    private String formatTotalValue(BigDecimal totalValue) {
        return Optional.ofNullable(totalValue)
                .map(REAL_FORMATTER::format)
                .orElse(null);
    }

    private String formatIssuanceDate(LocalDateTime issuanceDate) {
        return Optional.ofNullable(issuanceDate)
                .map(date -> date.format(DATE_FORMATTER))
                .orElse(null);
    }

    private String buildFileName(Order order) {
        return MessageFormat.format(FILE_NAME_TEMPLATE, order.getInvoiceKey());
    }
}