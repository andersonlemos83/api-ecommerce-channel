package br.com.alc.ecommerce.channel.infrastructure.cucumber.context;

import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.document.DocumentDataTable;
import br.com.alc.ecommerce.channel.infrastructure.helper.fixture.ResourceFixture;
import br.com.alc.ecommerce.channel.infrastructure.helper.repository.OrderRepositoryHelper;
import br.com.alc.ecommerce.channel.infrastructure.helper.util.ObjectMapperHelper;
import br.com.alc.ecommerce.channel.infrastructure.persistence.document.OrderDocument;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
@AllArgsConstructor
public class OrderDocumentContext {

    private final OrderRepositoryHelper orderRepositoryHelper;

    public void insert(List<DocumentDataTable> documentDataTableList) {
        List<OrderDocument> orders = documentDataTableList.stream()
                .map(DocumentDataTable::getJson)
                .map(ObjectMapperHelper::generateOrderDocument)
                .toList();
        orderRepositoryHelper.saveAll(orders);
    }

    public void insert(long quantity) {
        String json = ResourceFixture.getContentFromResourceJson("/fixtures/OrderDocument-987654383.json");
        for (int i = 0; i < quantity; i++) {
            OrderDocument orderDocument = ObjectMapperHelper.generateOrderDocument(json);
            String orderNumber = String.valueOf(ThreadLocalRandom.current().nextInt(999999999));
            orderDocument.getOrderRequest().setOrderNumber(orderNumber);
            orderRepositoryHelper.save(orderDocument);
        }
    }
}