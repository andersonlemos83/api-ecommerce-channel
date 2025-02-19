package br.com.alc.ecommerce.channel.core.domain.order;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public final class OrderRequest implements Serializable {

    private String channelCode;
    private String companyCode;
    private String storeCode;
    private Integer pos;
    private String orderNumber;
    private BigDecimal totalValue;
    private BigDecimal freightValue;
    private Customer customer;
    private List<ShoppingCartItem> items;
    private List<Payment> payments;

    private OrderStatus status;
    private String errorReason;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}