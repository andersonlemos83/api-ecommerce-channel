package br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessagingDataTable implements Serializable {

    private String queueName;
    private String jsonKey;

}