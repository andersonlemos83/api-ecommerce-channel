package br.com.alc.ecommerce.channel.infrastructure.dto.callback;

import br.com.alc.ecommerce.channel.core.domain.order.SaleStatus;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderCallbackRequestDto implements Serializable {

    private String orderNumber;

    private String invoiceKey;
    private String invoiceNumber;
    private LocalDateTime issuanceDate;
    private String invoiceBase64;

    private SaleStatus status;
    private String errorReason;

}