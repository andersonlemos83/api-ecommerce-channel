package br.com.alc.ecommerce.channel.infrastructure.adapter.input;

import br.com.alc.ecommerce.channel.infrastructure.dto.finder.OrderFinderRequestDto;
import br.com.alc.ecommerce.channel.infrastructure.dto.finder.OrderFinderResponseDto;
import reactor.core.publisher.Flux;

public interface ByPeriodOrderFinderInAdapter {

    Flux<OrderFinderResponseDto> execute(OrderFinderRequestDto orderFinderRequestDto);

}