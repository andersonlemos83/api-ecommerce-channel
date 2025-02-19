package br.com.alc.ecommerce.channel.infrastructure.dto.order;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartItemDto implements Serializable {

    private BigInteger code;
    private Integer quantity;
    private BigDecimal value;

}