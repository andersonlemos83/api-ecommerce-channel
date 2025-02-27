package br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.order;

import br.com.alc.ecommerce.channel.core.service.generator.stub.ZipCodeGeneratorServiceStub;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.order.CustomerDataTable;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.order.OrderRequestDataTable;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.order.PaymentDataTable;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.order.ShoppingCartItemDataTable;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.stepdefs.StepDefs;
import io.cucumber.java.pt.E;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class OrderRequestStepDefs extends StepDefs {

    @E("^que seja gerado os seguintes CEPs$")
    public void queSejaGeradoOsSeguintesCeps(List<String> ceps) {
        ceps.forEach(ZipCodeGeneratorServiceStub::createCep);
    }

    @E("^que seja gerado os seguintes dados de Order Request$")
    @E("^que seja informado os dados de Order Request$")
    public void queSejaInformadoOsDadosDeOrderRequest(List<OrderRequestDataTable> orderRequestDataTableList) {
        orderRequestDataTableList.forEach(transitionDataTable::setOrderRequestDataTable);
    }

    @E("^que seja gerado os seguintes dados de Customer$$")
    @E("^que seja informado os dados de Customer$")
    public void queSejaInformadoOsDadosDeCustomer$(List<CustomerDataTable> customerDataTableList) {
        customerDataTableList.forEach(transitionDataTable::setCustomerDataTable);
    }

    @E("^que seja gerado os seguintes dados de Shopping Cart Item")
    @E("^que seja informado os dados de Shopping Cart Item$")
    public void queSejaInformadoOsDadosDeShoppingCartItem(List<ShoppingCartItemDataTable> shoppingCartItemDataTableList) {
        transitionDataTable.setShoppingCartItemDataTableList(shoppingCartItemDataTableList);
    }

    @E("^que seja gerado os seguintes dados de Payment$")
    @E("^que seja informado os dados de Payment$")
    public void queSejaInformadoOsDadosDePayment$(List<PaymentDataTable> paymentDataTableList) {
        transitionDataTable.setPaymentDataTableList(paymentDataTableList);
    }
}