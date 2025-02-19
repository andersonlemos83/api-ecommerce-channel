package br.com.alc.ecommerce.channel.infrastructure.dto.bot;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderBotRequestDto implements Serializable {

    @Min(1)
    @Size(min = 1, max = 4)
    @NotNull(message = "não foi informado")
    @Schema(description = "Quantity of orders generated", example = "100")
    private Integer orderQuantity;

}