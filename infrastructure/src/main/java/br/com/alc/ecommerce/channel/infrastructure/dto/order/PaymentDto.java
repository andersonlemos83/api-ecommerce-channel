package br.com.alc.ecommerce.channel.infrastructure.dto.order;

import br.com.alc.ecommerce.channel.core.domain.order.PaymentMethod;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto implements Serializable {

    private PaymentMethod paymentMethod;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime paymentDate;
    private String authorizationCode;
    private String cardNumber;
    private String pixKey;
    private BigDecimal value;

}