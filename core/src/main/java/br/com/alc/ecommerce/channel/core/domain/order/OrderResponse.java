package br.com.alc.ecommerce.channel.core.domain.order;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import static br.com.alc.ecommerce.channel.core.domain.order.SaleStatus.ERROR;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public final class OrderResponse implements Serializable {

    private SaleStatus status;
    private LocalDateTime date;

    public boolean isError() {
        return ERROR.equals(status);
    }
}