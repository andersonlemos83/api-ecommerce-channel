package br.com.alc.ecommerce.channel.core.domain.order;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static br.com.alc.ecommerce.channel.core.domain.order.OrderStatus.INVOICED;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public final class Order implements Serializable {

    private String id;

    private OrderRequest orderRequest;
    private String invoiceKey;
    private String invoiceNumber;
    private LocalDateTime issuanceDate;
    private String invoiceBase64;

    private OrderStatus status;
    private String errorReason;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public String fetchOrderNumber() {
        return Optional.ofNullable(orderRequest)
                .map(OrderRequest::getOrderNumber)
                .orElse(null);
    }

    public BigDecimal fetchTotalValue() {
        return Optional.ofNullable(orderRequest)
                .map(OrderRequest::getTotalValue)
                .orElse(null);
    }

    public String fetchCustomerEmail() {
        return Optional.ofNullable(orderRequest)
                .map(OrderRequest::fetchCustomerEmail)
                .orElse(null);
    }

    public boolean isInvoiced() {
        return INVOICED.equals(status);
    }
}