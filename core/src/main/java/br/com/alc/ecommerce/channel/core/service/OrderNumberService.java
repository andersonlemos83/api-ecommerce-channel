package br.com.alc.ecommerce.channel.core.service;

import br.com.alc.ecommerce.channel.core.domain.bot.OrderBotRequest;
import reactor.core.publisher.Flux;

public interface OrderNumberService {

    Flux<String> execute(OrderBotRequest orderBotRequest);

}