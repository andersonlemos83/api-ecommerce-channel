package br.com.alc.ecommerce.channel.infrastructure.dto.finder;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderFinderRequestDto implements Serializable {

    private LocalDateTime startPeriodDate;
    private LocalDateTime endPeriodDate;
    private Integer pageNumber;
    private Integer pageSize;

}