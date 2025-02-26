package br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.support;

import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.callback.OrderCallbackRequestDataTable;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.generator.OrderGeneratorRequestDataTable;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.order.CustomerDataTable;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.order.OrderRequestDataTable;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.order.PaymentDataTable;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.order.ShoppingCartItemDataTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransitionDataTable implements Serializable {

    private WebTestClient.ResponseSpec response;

    private OrderGeneratorRequestDataTable orderGeneratorRequestDataTable;

    private OrderRequestDataTable orderRequestDataTable;
    private CustomerDataTable customerDataTable;
    private List<ShoppingCartItemDataTable> shoppingCartItemDataTableList;
    private List<PaymentDataTable> paymentDataTableList;

    private OrderCallbackRequestDataTable orderCallbackRequestDataTable;

    public OrderRequestDataTable buildOrderRequestDataTable() {
        if (orderRequestDataTable == null) {
            orderRequestDataTable = OrderRequestDataTable.builder().build();
        }

        if (customerDataTable != null) {
            orderRequestDataTable.setCustomer(customerDataTable);
        }

        if (shoppingCartItemDataTableList != null) {
            orderRequestDataTable.setItems(shoppingCartItemDataTableList);
        }

        if (paymentDataTableList != null) {
            orderRequestDataTable.setPayments(paymentDataTableList);
        }

        return orderRequestDataTable;
    }
}