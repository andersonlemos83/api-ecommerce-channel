package br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.finder;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderFinderRequestDataTable implements Serializable {

    private LocalDate startPeriodDate;
    private LocalDate endPeriodDate;
    private Integer pageNumber;
    private Integer pageSize;

}