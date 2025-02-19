package br.com.alc.ecommerce.channel.infrastructure.web.controller;

import br.com.alc.ecommerce.channel.infrastructure.adapter.input.OrderNumberGeneratorInAdapter;
import br.com.alc.ecommerce.channel.infrastructure.dto.bot.OrderBotRequestDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil.generateJson;
import static org.springframework.http.HttpStatus.CREATED;

@Log4j2
@RestController
@AllArgsConstructor
public class OrderController {

    private final OrderNumberGeneratorInAdapter orderNumberGeneratorInAdapter;

    @ResponseStatus(CREATED)
    @PostMapping(value = "start-order-bot")
    public Flux<String> startOrderBot(@Valid @RequestBody OrderBotRequestDto orderBotRequestDto) {
        log.info("---> Request POST /start-order-bot: {}", generateJson(orderBotRequestDto));
        return orderNumberGeneratorInAdapter.execute(orderBotRequestDto)
                .doFinally(responses -> log.info("<--- Response POST /start-order-bot: {}", generateJson(responses)));
    }
}