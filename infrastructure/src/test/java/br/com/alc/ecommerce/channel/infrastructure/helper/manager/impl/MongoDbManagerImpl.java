package br.com.alc.ecommerce.channel.infrastructure.helper.manager.impl;

import br.com.alc.ecommerce.channel.infrastructure.helper.manager.MongoDbManager;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

@Log4j2
@Component
@AllArgsConstructor
public class MongoDbManagerImpl implements MongoDbManager {

    private final MongoTemplate mongoTemplate;

    @Override
    public void cleanDatabase() {
        Set<String> collectionNames = mongoTemplate.getCollectionNames();
        log.info("--> Collections: {}", collectionNames);
        collectionNames.parallelStream()
                .forEach(collectionName -> {
                    mongoTemplate.getCollection(collectionName).drop();
                    log.info("--> Dropped collection: {}", collectionName);
                });
    }
}