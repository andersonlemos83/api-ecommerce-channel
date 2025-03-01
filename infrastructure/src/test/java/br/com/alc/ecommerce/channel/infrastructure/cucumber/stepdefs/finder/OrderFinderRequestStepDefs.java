package br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.finder;

import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.finder.OrderFinderRequestDataTable;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.StepDefs;
import io.cucumber.java.pt.E;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class OrderFinderRequestStepDefs extends StepDefs {

    @E("^que seja informado os dados de Order Finder Request$")
    public void queSejaInformadoOsDadosDeOrderFinderRequest(List<OrderFinderRequestDataTable> orderFinderRequestDataTableList) {
        orderFinderRequestDataTableList.forEach(transitionDataTable::setOrderFinderRequestDataTable);
    }
}