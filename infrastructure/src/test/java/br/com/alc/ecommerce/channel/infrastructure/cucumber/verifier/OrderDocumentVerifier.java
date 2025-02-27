package br.com.alc.ecommerce.channel.infrastructure.cucumber.verifier;

import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.context.DocumentDataTable;
import br.com.alc.ecommerce.channel.infrastructure.dto.order.OrderRequestDto;
import br.com.alc.ecommerce.channel.infrastructure.helper.repository.OrderRepositoryHelper;
import br.com.alc.ecommerce.channel.infrastructure.helper.util.ObjectMapperHelper;
import br.com.alc.ecommerce.channel.infrastructure.persistence.document.OrderDocument;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparing;
import static org.junit.Assert.assertEquals;

@Component
@AllArgsConstructor
public class OrderDocumentVerifier {

    private final OrderRepositoryHelper orderRepositoryHelper;

    public void verify(List<DocumentDataTable> documentDataTableList) {
        List<String> expecteds = documentDataTableList.stream()
                .map(DocumentDataTable::getJson)
                .map(ObjectMapperHelper::generateOrderDocument)
                .sorted(buildOrderDocumentComparator())
                .map(ObjectMapperHelper::generateJson)
                .toList();

        List<String> returneds = orderRepositoryHelper.findAll()
                .stream()
                .peek(orderDocument -> orderDocument.setId(null))
                .sorted(buildOrderDocumentComparator())
                .map(ObjectMapperHelper::generateJson)
                .toList();

        assertEquals("Should return the expected number of Orders.", expecteds.size(), returneds.size());

        for (int i = 0; i < documentDataTableList.size(); i++) {
            String expected = expecteds.get(i);
            String returned = returneds.get(i);

            assertEquals(expected, returned);
        }
    }

    private Comparator<OrderDocument> buildOrderDocumentComparator() {
        return comparing(document -> Optional.ofNullable(document).map(OrderDocument::getOrderRequest).map(OrderRequestDto::getOrderNumber).orElse(null));
    }
}