package br.com.alc.ecommerce.channel.infrastructure.adapter.input;

import br.com.alc.ecommerce.channel.infrastructure.dto.bot.OrderBotRequestDto;
import br.com.alc.ecommerce.channel.infrastructure.dto.bot.OrderBotResponseDto;
import reactor.core.publisher.Flux;

public interface OrderNumberGeneratorInAdapter {

    Flux<OrderBotResponseDto> execute(OrderBotRequestDto orderBotRequestDto);

}