package br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.order;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartItemDataTable implements Serializable {

    private BigInteger code;
    private Integer quantity;
    private BigDecimal value;

}