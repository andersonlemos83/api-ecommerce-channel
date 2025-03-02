package br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.email;

import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.email.EmailDataTable;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.StepDefs;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.verifier.EmailVerifier;
import io.cucumber.java.pt.E;
import lombok.AllArgsConstructor;

import java.util.List;

import static java.util.Collections.emptyList;

@AllArgsConstructor
public class EmailStepDefs extends StepDefs {

    private final EmailVerifier emailVerifier;

    @E("^deveria enviar os e-mails esperados$")
    public void deveriaEnviarOsEmailsEsperados(List<EmailDataTable> emailDataTableList) throws Exception {
        emailVerifier.verify(emailDataTableList);
    }

    @E("^nao deveria enviar nenhum e-mail")
    public void naoDeveriaEnviarNenhumEmail() throws Exception {
        emailVerifier.verify(emptyList());
    }
}