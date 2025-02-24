package br.com.alc.ecommerce.channel.core.service.validator;

import br.com.alc.ecommerce.channel.core.domain.finder.OrderFinderRequest;
import reactor.core.publisher.Mono;

public interface ByPeriodOrderFinderValidatorService {

    Mono<Void> validate(OrderFinderRequest orderFinderRequest);

}