package br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.generator;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderGeneratorRequestDataTable implements Serializable {

    private String orderNumber;

}