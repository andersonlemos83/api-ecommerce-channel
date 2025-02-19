package br.com.alc.ecommerce.channel.core.port.output;

import br.com.alc.ecommerce.channel.core.domain.generator.OrderGeneratorRequest;
import reactor.core.publisher.Flux;

public interface OrderNumberIntegratorOutPort {

    Flux<Void> execute(OrderGeneratorRequest orderGeneratorRequest);

}