package br.com.alc.ecommerce.channel.infrastructure.persistence.repository;

import br.com.alc.ecommerce.channel.infrastructure.persistence.document.OrderDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderReactiveRepository extends ReactiveMongoRepository<OrderDocument, String> {

}