package br.com.alc.ecommerce.channel.infrastructure.persistence.repository;

import br.com.alc.ecommerce.channel.infrastructure.persistence.document.OrderDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends MongoRepository<OrderDocument, String> {

    Optional<OrderDocument> findFirstByOrderRequest_OrderNumberOrderByUpdatedDateDesc(String orderNumber);

}