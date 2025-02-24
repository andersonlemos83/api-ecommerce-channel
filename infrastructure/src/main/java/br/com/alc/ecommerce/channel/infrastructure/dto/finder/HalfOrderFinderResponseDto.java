package br.com.alc.ecommerce.channel.infrastructure.dto.finder;

import br.com.alc.ecommerce.channel.core.domain.order.OrderStatus;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HalfOrderFinderResponseDto implements Serializable {

    private String id;

    private String channelCode;
    private String companyCode;
    private String storeCode;
    private Integer pos;
    private String orderNumber;
    private BigDecimal totalValue;
    private BigDecimal freightValue;

    private String invoiceKey;
    private String invoiceNumber;
    private LocalDateTime issuanceDate;

    private OrderStatus status;
    private String errorReason;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}