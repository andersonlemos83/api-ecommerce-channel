package br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.messaging;

import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.messaging.MessagingDataTable;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.StepDefs;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.verifier.MessagingVerifier;
import io.cucumber.java.pt.E;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class MessagingStepDefs extends StepDefs {

    private final MessagingVerifier messagingVerifier;

    @E("^deveria publicar o JSON esperado na fila$")
    public void deveriaPublicarOhJsonEsperadoNaFila(List<MessagingDataTable> messagingDataTableList) {
        messagingVerifier.verify(messagingDataTableList);
    }

    @E("^nao deveria publicar nenhum JSON na fila$")
    public void naoDeveriaPublicarNenhumJsonNaFila(List<MessagingDataTable> messagingDataTableList) {
        List<String> queues = messagingDataTableList.stream().map(MessagingDataTable::getQueueName).distinct().toList();
        messagingVerifier.verifyEmptyQueues(queues);
    }

    @E("^deveria publicar a quantidade esperada de mensagens na fila$")
    public void deveriaPublicarAhQuantidadeEsperadaDeMensagensNaFila(List<MessagingDataTable> messagingDataTableList) {
        messagingVerifier.verifyQuantityQueues(messagingDataTableList);
    }
}