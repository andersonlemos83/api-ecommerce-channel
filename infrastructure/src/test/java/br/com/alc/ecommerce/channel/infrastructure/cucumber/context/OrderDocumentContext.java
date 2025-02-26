package br.com.alc.ecommerce.channel.infrastructure.cucumber.context;

import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.document.DocumentDataTable;
import br.com.alc.ecommerce.channel.infrastructure.helper.repository.OrderRepositoryHelper;
import br.com.alc.ecommerce.channel.infrastructure.helper.util.ObjectMapperHelper;
import br.com.alc.ecommerce.channel.infrastructure.persistence.document.OrderDocument;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

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
}