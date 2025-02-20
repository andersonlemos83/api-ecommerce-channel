package br.com.alc.ecommerce.channel.infrastructure.dto.order;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto implements Serializable {

    private String channelCode;
    private String companyCode;
    private String storeCode;
    private Integer pos;
    private String orderNumber;
    private BigDecimal totalValue;
    private BigDecimal freightValue;
    private CustomerDto customer;
    private List<ShoppingCartItemDto> items;
    private List<PaymentDto> payments;

}