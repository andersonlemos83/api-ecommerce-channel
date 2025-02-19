package br.com.alc.ecommerce.channel.core.port.input;

import br.com.alc.ecommerce.channel.core.domain.bot.OrderBotRequest;
import br.com.alc.ecommerce.channel.core.domain.bot.OrderBotResponse;
import reactor.core.publisher.Flux;

public interface OrderNumberGeneratorUseCase {

    Flux<OrderBotResponse> execute(OrderBotRequest orderBotRequest);

}