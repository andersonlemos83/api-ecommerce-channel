package br.com.alc.ecommerce.channel.core.domain.order;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public final class OrderResponse implements Serializable {

    private SaleStatus status;
    private LocalDateTime date;

}