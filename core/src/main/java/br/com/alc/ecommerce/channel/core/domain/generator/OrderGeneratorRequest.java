package br.com.alc.ecommerce.channel.core.domain.generator;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderGeneratorRequest implements Serializable {

    private String orderNumber;

}