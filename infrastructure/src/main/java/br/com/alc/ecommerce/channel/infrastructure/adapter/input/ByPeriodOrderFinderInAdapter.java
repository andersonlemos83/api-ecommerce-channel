package br.com.alc.ecommerce.channel.infrastructure.adapter.input;

import br.com.alc.ecommerce.channel.infrastructure.dto.finder.HalfOrderFinderResponseDto;
import br.com.alc.ecommerce.channel.infrastructure.dto.finder.OrderFinderRequestDto;
import reactor.core.publisher.Flux;

public interface ByPeriodOrderFinderInAdapter {

    Flux<HalfOrderFinderResponseDto> execute(OrderFinderRequestDto orderFinderRequestDto);

}