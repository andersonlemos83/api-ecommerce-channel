package br.com.alc.ecommerce.channel.infrastructure.dto.order;

import br.com.alc.ecommerce.channel.core.domain.order.SaleStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class OrderResponseDto implements Serializable {

    private SaleStatus status;
    private LocalDateTime date;

}