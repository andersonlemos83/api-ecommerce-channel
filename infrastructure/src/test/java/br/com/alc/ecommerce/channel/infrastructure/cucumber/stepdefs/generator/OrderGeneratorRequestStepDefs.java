package br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.generator;

import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.generator.OrderGeneratorRequestDataTable;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.StepDefs;
import io.cucumber.java.pt.E;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class OrderGeneratorRequestStepDefs extends StepDefs {

    @E("^que seja informado os dados de Order Generator Request$")
    public void queSejaInformadoOsDadosDeOrderGeneratorRequest(List<OrderGeneratorRequestDataTable> orderGeneratorRequestDataTableList) {
        orderGeneratorRequestDataTableList.forEach(transitionDataTable::setOrderGeneratorRequestDataTable);
    }
}