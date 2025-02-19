package br.com.alc.ecommerce.channel.infrastructure.dto.order;

import br.com.alc.ecommerce.channel.core.domain.order.DocumentType;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto implements Serializable {

    private String name;
    private String document;
    private DocumentType documentType;
    private String address;
    private String addressNumber;
    private String addressComplement;
    private String neighborhood;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private String phone;
    private String email;

}