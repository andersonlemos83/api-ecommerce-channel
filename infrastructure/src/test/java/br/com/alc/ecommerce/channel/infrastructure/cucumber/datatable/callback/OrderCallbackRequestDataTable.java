package br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.callback;

import br.com.alc.ecommerce.channel.core.domain.order.SaleStatus;
import br.com.alc.ecommerce.channel.infrastructure.helper.fixture.ResourceFixture;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import static lombok.AccessLevel.NONE;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderCallbackRequestDataTable implements Serializable {

    private String orderNumber;

    private String invoiceKey;
    private String invoiceNumber;
    private LocalDateTime issuanceDate;
    @Getter(NONE)
    private String invoiceBase64;

    private SaleStatus status;
    private String errorReason;

    public String getInvoiceBase64() {
        if (invoiceBase64 == null || "".equalsIgnoreCase(invoiceBase64)) {
            return null;
        }
        if (invoiceBase64.startsWith("/fixtures/")) {
            return ResourceFixture.getContentFromResource(invoiceBase64);
        }
        return invoiceBase64;
    }
}