package br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.verifier;

import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.context.DocumentDataTable;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.StepDefs;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.verifier.OrderDocumentVerifier;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import lombok.AllArgsConstructor;

import java.util.List;

import static java.util.Collections.emptyList;

@AllArgsConstructor
public class DocumentVerifierStepDefs extends StepDefs {

    private final OrderDocumentVerifier orderDocumentVerifier;

    @Entao("^deveria existir as seguintes Order Document na base$")
    public void deveriaExistirAsSeguintesOrderDocumentNaBase(List<DocumentDataTable> documentDataTableList) {
        orderDocumentVerifier.verify(documentDataTableList);
    }

    @E("^nao deveria existir nenhum Order Document na base$")
    public void naoDeveriaExistirNenhumOrderDocumentNaBase() {
        orderDocumentVerifier.verify(emptyList());
    }
}