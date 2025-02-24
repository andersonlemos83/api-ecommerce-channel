package br.com.alc.ecommerce.channel.infrastructure.web.controller;

import br.com.alc.ecommerce.channel.infrastructure.adapter.input.ByOrderNumberOrderFinderInAdapter;
import br.com.alc.ecommerce.channel.infrastructure.adapter.input.ByPeriodOrderFinderInAdapter;
import br.com.alc.ecommerce.channel.infrastructure.adapter.input.OrderNumberGeneratorInAdapter;
import br.com.alc.ecommerce.channel.infrastructure.dto.bot.OrderBotRequestDto;
import br.com.alc.ecommerce.channel.infrastructure.dto.bot.OrderBotResponseDto;
import br.com.alc.ecommerce.channel.infrastructure.dto.finder.OrderFinderRequestDto;
import br.com.alc.ecommerce.channel.infrastructure.dto.finder.OrderFinderResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil.generateJson;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderNumberGeneratorInAdapter orderNumberGeneratorInAdapter;
    private final ByOrderNumberOrderFinderInAdapter byOrderNumberOrderFinderInAdapter;
    private final ByPeriodOrderFinderInAdapter byPeriodOrderFinderInAdapter;

    @ResponseStatus(CREATED)
    @PostMapping(value = "/start-bot", produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<OrderBotResponseDto> startOrderBot(@Valid @RequestBody OrderBotRequestDto orderBotRequestDto) {
        log.info("---> Request POST /order/start-bot: {}", generateJson(orderBotRequestDto));
        return orderNumberGeneratorInAdapter.execute(orderBotRequestDto)
                .doOnNext(responses -> log.info("<--- Response POST /order/start-bot: {}", generateJson(responses)));
    }

    @GetMapping(value = "/{orderNumber}", produces = TEXT_EVENT_STREAM_VALUE)
    public Mono<OrderFinderResponseDto> findOrdersByOrderNumber(@PathVariable("orderNumber") String orderNumber) {
        log.info("---> Request GET /order/{}", orderNumber);
        return byOrderNumberOrderFinderInAdapter.execute(orderNumber)
                .doFinally(responses -> log.info("<--- Response GET /order/{}: {}", orderNumber, generateJson(responses)));
    }

    @GetMapping(value = "/paginated", produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<OrderFinderResponseDto> findOrdersByPeriod(@RequestParam OrderFinderRequestDto orderFinderRequestDto) {
        log.info("---> Request GET /order/paginated: {}", generateJson(orderFinderRequestDto));
        return byPeriodOrderFinderInAdapter.execute(orderFinderRequestDto)
                .doFinally(responses -> log.info("<--- Response GET /order/paginated: {}", generateJson(responses)));
    }
}