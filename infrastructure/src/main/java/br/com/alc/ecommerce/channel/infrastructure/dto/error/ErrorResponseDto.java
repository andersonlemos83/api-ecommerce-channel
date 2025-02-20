package br.com.alc.ecommerce.channel.infrastructure.dto.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public final class ErrorResponseDto implements Serializable {

    @Schema(description = "Response HTTP status", example = "BAD_REQUEST")
    private HttpStatus httpStatus;

    @Schema(description = "Response error message", example = "O campo totalValue não foi informado.")
    private String message;

}