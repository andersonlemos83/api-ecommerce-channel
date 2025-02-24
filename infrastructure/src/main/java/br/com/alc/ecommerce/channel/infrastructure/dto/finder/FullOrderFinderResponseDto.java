package br.com.alc.ecommerce.channel.infrastructure.dto.finder;

import br.com.alc.ecommerce.channel.core.domain.order.OrderStatus;
import br.com.alc.ecommerce.channel.infrastructure.dto.order.OrderRequestDto;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FullOrderFinderResponseDto implements Serializable {

    private String id;

    private OrderRequestDto orderRequest;
    private String invoiceKey;
    private String invoiceNumber;
    private LocalDateTime issuanceDate;
    private String invoiceBase64;

    private OrderStatus status;
    private String errorReason;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}