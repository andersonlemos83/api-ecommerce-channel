package br.com.alc.ecommerce.channel.core.port.input;

import br.com.alc.ecommerce.channel.core.domain.generator.OrderGeneratorRequest;

public interface OrderGeneratorUseCase {

    void execute(OrderGeneratorRequest orderGeneratorRequest);

}