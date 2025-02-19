package br.com.alc.ecommerce.channel.core.service;

import br.com.alc.ecommerce.channel.core.domain.bot.OrderBotRequest;
import br.com.alc.ecommerce.channel.core.domain.bot.OrderBotResponse;
import reactor.core.publisher.Flux;

public interface OrderNumberService {

    Flux<OrderBotResponse> execute(OrderBotRequest orderBotRequest);

}