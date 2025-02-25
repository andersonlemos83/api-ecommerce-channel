package br.com.alc.ecommerce.channel.infrastructure.cucumber.verifier;

import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.messaging.MessagingDataTable;
import br.com.alc.ecommerce.channel.infrastructure.helper.fixture.JsonFixture;
import br.com.alc.ecommerce.channel.infrastructure.helper.manager.RabbitMqManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;
import static org.junit.Assert.*;

@Component
@AllArgsConstructor
public class MessagingVerifier {

    private final RabbitMqManager rabbitMqManager;

    public void verify(List<MessagingDataTable> messagingDataTableList) {
        messagingDataTableList.forEach(this::verify);
    }

    public void verify(MessagingDataTable messagingDataTable) {
        String expectedJson = getExpectedJson(messagingDataTable);
        String returnedJson = getQueueJson(messagingDataTable);

        String errorMessage = MessageFormat.format("Expected message should be published in the queue {0}", messagingDataTable.getQueueName());
        assertNotNull(errorMessage, expectedJson);
        assertEquals(errorMessage, expectedJson, returnedJson);
    }

    public void verifyEmptyQueues(List<String> queues) {
        queues.forEach(this::verifyEmptyQueue);
    }

    public void verifyEmptyQueue(String queueName) {
        List<String> messages = rabbitMqManager.getMessages(queueName);
        String errorMessage = MessageFormat.format("No message should be published in the queue {0}", queueName);
        assertTrue(errorMessage, messages.isEmpty());
    }

    private String getExpectedJson(MessagingDataTable messagingDataTable) {
        return JsonFixture.oneJson(messagingDataTable);
    }

    private String getQueueJson(MessagingDataTable messagingDataTable) {
        List<String> messages = rabbitMqManager.getMessages(messagingDataTable.getQueueName());
        return Optional.ofNullable(messages)
                .filter(m -> !m.isEmpty())
                .map(m -> m.stream().collect(joining("; ", "", "")))
                .orElse(null);
    }
}