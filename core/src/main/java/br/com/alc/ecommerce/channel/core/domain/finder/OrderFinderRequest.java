package br.com.alc.ecommerce.channel.core.domain.finder;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.time.LocalTime.MAX;
import static java.time.LocalTime.MIN;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public final class OrderFinderRequest implements Serializable {

    private LocalDate startPeriodDate;
    private LocalDate endPeriodDate;
    private Integer pageNumber;
    private Integer pageSize;

    public LocalDateTime generateStartPeriodLocalDateTime() {
        return LocalDateTime.of(startPeriodDate, MIN);
    }

    public LocalDateTime generateEndPeriodLocalDateTime() {
        return LocalDateTime.of(endPeriodDate, MAX);
    }

    public boolean isPeriodInvalid() {
        return !generateStartPeriodLocalDateTime().isBefore(generateEndPeriodLocalDateTime());
    }
}