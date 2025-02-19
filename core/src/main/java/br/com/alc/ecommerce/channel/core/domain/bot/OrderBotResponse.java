package br.com.alc.ecommerce.channel.core.domain.bot;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderBotResponse implements Serializable {

    private String orderNumber;

}