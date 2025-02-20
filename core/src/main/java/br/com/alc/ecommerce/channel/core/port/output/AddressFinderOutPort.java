package br.com.alc.ecommerce.channel.core.port.output;

import br.com.alc.ecommerce.channel.core.domain.location.AddressResponse;

public interface AddressFinderOutPort {

    AddressResponse execute(String cep);

}