package br.com.alc.ecommerce.channel.infrastructure.dto.generator;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderGeneratorRequestDto implements Serializable {

    private String orderNumber;

}