package br.com.alc.ecommerce.channel.infrastructure.dto.location;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDto implements Serializable {

    @JsonProperty("cep")
    private String zipCode;

    @JsonProperty("logradouro")
    private String street;

    @JsonProperty("complemento")
    private String complement;

    @JsonProperty("bairro")
    private String neighborhood;

    @JsonProperty("localidade")
    private String city;

    @JsonProperty("uf")
    private String state;

    @JsonProperty("ddd")
    private String ddd;

    @JsonProperty("erro")
    private boolean erro;

}