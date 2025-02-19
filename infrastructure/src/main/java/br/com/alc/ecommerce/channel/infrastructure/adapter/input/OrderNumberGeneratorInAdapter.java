package br.com.alc.ecommerce.channel.infrastructure.adapter.input;

import br.com.alc.ecommerce.channel.infrastructure.dto.bot.OrderBotRequestDto;
import reactor.core.publisher.Flux;

public interface OrderNumberGeneratorInAdapter {

    Flux<String> execute(OrderBotRequestDto orderBotRequestDto);

}