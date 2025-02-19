package br.com.alc.ecommerce.channel.core.port.input;

import br.com.alc.ecommerce.channel.core.domain.bot.OrderBotRequest;
import reactor.core.publisher.Flux;

public interface OrderNumberGeneratorUseCase {

    Flux<String> execute(OrderBotRequest orderBotRequest);

}