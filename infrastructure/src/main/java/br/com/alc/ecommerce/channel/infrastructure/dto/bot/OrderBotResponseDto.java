package br.com.alc.ecommerce.channel.infrastructure.dto.bot;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderBotResponseDto implements Serializable {

    @Schema(description = "Order numbers")
    private List<String> orderNumbers;

}