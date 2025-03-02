package br.com.alc.ecommerce.channel.core.domain.order;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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

    public String fetchCustomerEmail() {
        return Optional.ofNullable(customer)
                .map(Customer::getEmail)
                .orElse(null);
    }
}