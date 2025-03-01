package br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.watch;

import br.com.alc.ecommerce.channel.core.service.watch.stub.VirtualWatchService;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.watch.WatchDataTable;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.StepDefs;
import io.cucumber.java.pt.E;

import java.util.List;

public class WatchStepDefs extends StepDefs {

    @E("^que o sistema seja executado na seguinte data e hora$")
    public void queOhSistemaSejaExecutadoNasSeguintesDataIhHora(List<WatchDataTable> watchDataTableList) {
        watchDataTableList.forEach(VirtualWatchService::createDateAndTime);
    }
}