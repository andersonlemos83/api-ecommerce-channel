package br.com.alc.ecommerce.channel.core.domain.order;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public final class SaleRequest implements Serializable {

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

}