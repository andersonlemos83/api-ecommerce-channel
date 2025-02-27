package br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.bot;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderBotRequestDataTable implements Serializable {

    private Integer orderQuantity;

}