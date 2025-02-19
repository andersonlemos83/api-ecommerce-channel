package br.com.alc.ecommerce.channel.core.service.generator;

import br.com.alc.ecommerce.channel.core.domain.bot.OrderBotRequest;
import reactor.core.publisher.Flux;

public interface OrderNumberGeneratorService {

    Flux<String> execute(OrderBotRequest orderBotRequest);

}