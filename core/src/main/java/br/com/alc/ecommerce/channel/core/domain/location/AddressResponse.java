package br.com.alc.ecommerce.channel.core.domain.location;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public final class AddressResponse implements Serializable {

    private String zipCode;
    private String street;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
    private String ddd;

    private boolean erro;

}