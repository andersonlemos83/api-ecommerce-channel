package br.com.alc.ecommerce.channel.core.service.generator;

import br.com.alc.ecommerce.channel.core.domain.generator.OrderGeneratorRequest;
import br.com.alc.ecommerce.channel.core.domain.address.AddressResponse;
import br.com.alc.ecommerce.channel.core.domain.order.OrderRequest;

public interface OrderGeneratorService {

    OrderRequest execute(OrderGeneratorRequest orderGeneratorRequest, AddressResponse addressResponse);

}