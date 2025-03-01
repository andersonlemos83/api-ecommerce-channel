package br.com.alc.ecommerce.channel.infrastructure.helper.repository;

import br.com.alc.ecommerce.channel.infrastructure.persistence.document.OrderDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepositoryHelper extends MongoRepository<OrderDocument, String> {

}