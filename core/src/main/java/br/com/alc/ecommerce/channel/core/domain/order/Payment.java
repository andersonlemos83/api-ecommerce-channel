package br.com.alc.ecommerce.channel.core.domain.order;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public final class Payment implements Serializable {

    private PaymentMethod paymentMethod;
    private LocalDateTime paymentDate;
    private String authorizationCode;
    private String cardNumber;
    private String pixKey;
    private BigDecimal value;

}