package br.com.alc.ecommerce.channel.infrastructure.adapter.input;

import br.com.alc.ecommerce.channel.infrastructure.dto.finder.FullOrderFinderResponseDto;
import reactor.core.publisher.Mono;

public interface ByOrderNumberOrderFinderInAdapter {

    Mono<FullOrderFinderResponseDto> execute(String orderNumber);

}