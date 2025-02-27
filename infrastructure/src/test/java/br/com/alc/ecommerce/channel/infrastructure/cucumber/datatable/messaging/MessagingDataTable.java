package br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.messaging;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MessagingDataTable implements Serializable {

    @EqualsAndHashCode.Include
    private String queueName;

    @EqualsAndHashCode.Include
    private String jsonKey;

    private Integer quantity;

}