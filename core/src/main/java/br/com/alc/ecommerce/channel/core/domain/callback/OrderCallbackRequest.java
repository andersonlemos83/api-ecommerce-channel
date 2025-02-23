package br.com.alc.ecommerce.channel.core.domain.callback;

import br.com.alc.ecommerce.channel.core.domain.order.SaleStatus;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import static br.com.alc.ecommerce.channel.core.domain.order.SaleStatus.ERROR;
import static br.com.alc.ecommerce.channel.core.domain.order.SaleStatus.PROCESSED;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public final class OrderCallbackRequest implements Serializable {

    private String orderNumber;

    private String invoiceKey;
    private String invoiceNumber;
    private LocalDateTime issuanceDate;
    private String invoiceBase64;

    private SaleStatus status;
    private String errorReason;

    public boolean isNotProcessedOrError() {
        return !(PROCESSED.equals(status) || ERROR.equals(status));
    }
}