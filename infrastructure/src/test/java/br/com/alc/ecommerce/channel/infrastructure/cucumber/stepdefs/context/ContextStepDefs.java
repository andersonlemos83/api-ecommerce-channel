package br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.context;

import br.com.alc.ecommerce.channel.infrastructure.cucumber.context.OrderDocumentContext;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.context.DocumentDataTable;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.StepDefs;
import io.cucumber.java.pt.E;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ContextStepDefs extends StepDefs {

    private final OrderDocumentContext orderDocumentContext;

    @E("^que existam as Order Document cadastradas$")
    public void queExistamAsOrderDocumentCadastradas(List<DocumentDataTable> documentDataTableList) {
        orderDocumentContext.insert(documentDataTableList);
    }

    @E("^que existam \"([^\"]*)\" Order Document cadastradas$")
    public void queExistamNOrderDocumentCadastradas(long quantity) {
        orderDocumentContext.insert(quantity);
    }
}