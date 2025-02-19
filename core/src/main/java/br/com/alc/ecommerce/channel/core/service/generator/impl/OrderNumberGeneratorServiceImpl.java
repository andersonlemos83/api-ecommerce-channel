package br.com.alc.ecommerce.channel.core.service.generator.impl;

import br.com.alc.ecommerce.channel.core.domain.bot.OrderBotRequest;
import br.com.alc.ecommerce.channel.core.service.generator.OrderNumberGeneratorService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

import java.util.Random;

@Log4j2
@AllArgsConstructor
public class OrderNumberGeneratorServiceImpl implements OrderNumberGeneratorService {

    private static final int BOUND = 999999999;

    @Override
    public Flux<String> execute(OrderBotRequest orderBotRequest) {
        return Flux.range(0, orderBotRequest.getOrderQuantity())
                .map(i -> new Random().nextInt(BOUND))
                .map(String::valueOf);
    }
}