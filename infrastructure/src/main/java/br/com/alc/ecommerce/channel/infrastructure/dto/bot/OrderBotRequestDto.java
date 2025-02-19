package br.com.alc.ecommerce.channel.infrastructure.dto.bot;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderBotRequestDto implements Serializable {

    @Min(1)
    @Max(1000)
    @NotNull(message = "não foi informado")
    @Schema(description = "Quantity of orders generated", example = "100")
    private Integer orderQuantity;

}