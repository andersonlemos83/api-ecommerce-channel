package br.com.alc.ecommerce.channel.core.domain.order;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public final class Customer implements Serializable {

    private String name;
    private String document;
    private DocumentType documentType;
    private String phone;
    private String email;

    private String address;
    private String addressNumber;
    private String addressComplement;
    private String neighborhood;
    private String city;
    private String state;
    private String country;
    private String zipCode;

}