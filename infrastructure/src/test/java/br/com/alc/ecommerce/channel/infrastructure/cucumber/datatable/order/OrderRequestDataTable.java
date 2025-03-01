package br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.order;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDataTable implements Serializable {

    private String channelCode;
    private String companyCode;
    private String storeCode;
    private Integer pos;
    private String orderNumber;
    private BigDecimal totalValue;
    private BigDecimal freightValue;
    private CustomerDataTable customer;
    private List<ShoppingCartItemDataTable> items;
    private List<PaymentDataTable> payments;

}