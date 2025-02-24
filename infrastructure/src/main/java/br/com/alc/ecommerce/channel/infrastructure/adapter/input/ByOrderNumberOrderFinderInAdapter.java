package br.com.alc.ecommerce.channel.infrastructure.adapter.input;

import br.com.alc.ecommerce.channel.infrastructure.dto.finder.OrderFinderResponseDto;
import reactor.core.publisher.Mono;

public interface ByOrderNumberOrderFinderInAdapter {

    Mono<OrderFinderResponseDto> execute(String orderNumber);

}