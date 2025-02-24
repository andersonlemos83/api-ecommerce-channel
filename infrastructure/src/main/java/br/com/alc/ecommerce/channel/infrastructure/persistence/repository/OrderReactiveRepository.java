package br.com.alc.ecommerce.channel.infrastructure.persistence.repository;

import br.com.alc.ecommerce.channel.infrastructure.persistence.document.OrderDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public interface OrderReactiveRepository extends ReactiveMongoRepository<OrderDocument, String> {

    Mono<OrderDocument> findFirstByOrderRequest_OrderNumberOrderByUpdatedDateDesc(String orderNumber);

    Flux<OrderDocument> findAllByCreatedDateBetweenOrderByCreatedDateDesc(LocalDateTime from, LocalDateTime to, Pageable pageable);

}