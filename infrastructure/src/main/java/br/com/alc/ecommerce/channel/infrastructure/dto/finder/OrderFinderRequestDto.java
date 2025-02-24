package br.com.alc.ecommerce.channel.infrastructure.dto.finder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderFinderRequestDto implements Serializable {

    @NotNull(message = "não foi informado")
    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Schema(description = "Start period date", example = "2025-02-23")
    private LocalDate startPeriodDate;

    @NotNull(message = "não foi informado")
    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Schema(description = "End period date", example = "2025-02-24")
    private LocalDate endPeriodDate;

    @Min(0)
    @NotNull(message = "não foi informado")
    @Schema(description = "Page number", example = "0")
    private Integer pageNumber;

    @Min(10)
    @Max(100)
    @NotNull(message = "não foi informado")
    @Schema(description = "Page size", example = "20")
    private Integer pageSize;

}