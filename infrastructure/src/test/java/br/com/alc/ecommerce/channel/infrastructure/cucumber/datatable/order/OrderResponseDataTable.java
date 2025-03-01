package br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.order;

import br.com.alc.ecommerce.channel.core.domain.order.SaleStatus;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDataTable implements Serializable {

    private SaleStatus status;
    private LocalDateTime date;

}