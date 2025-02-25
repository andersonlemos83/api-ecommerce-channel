package br.com.alc.ecommerce.channel.core.service.generator.stub;

import br.com.alc.ecommerce.channel.core.domain.address.AddressResponse;
import br.com.alc.ecommerce.channel.core.domain.generator.OrderGeneratorRequest;
import br.com.alc.ecommerce.channel.core.domain.order.Customer;
import br.com.alc.ecommerce.channel.core.domain.order.OrderRequest;
import br.com.alc.ecommerce.channel.core.service.generator.OrderGeneratorService;
import br.com.alc.ecommerce.channel.core.service.watch.stub.VirtualWatchService;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.order.OrderRequestDataTable;
import br.com.alc.ecommerce.channel.infrastructure.helper.util.ObjectMapperHelper;

import java.util.Optional;

import static java.util.Collections.emptyList;

public class OrderGeneratorServiceStub implements OrderGeneratorService {

    private static OrderRequest orderRequest;

    static {
        resetOrderRequest();
    }

    @Override
    public OrderRequest execute(OrderGeneratorRequest orderGeneratorRequest, AddressResponse addressResponse) {
        orderRequest.setOrderNumber(orderGeneratorRequest.getOrderNumber());

        Optional.ofNullable(orderRequest.getCustomer())
                .ifPresent(customer -> {
                    customer.setAddress(addressResponse.getStreet());
                    customer.setAddressComplement(addressResponse.getComplement());
                    customer.setNeighborhood(addressResponse.getNeighborhood());
                    customer.setCity(addressResponse.getCity());
                    customer.setState(addressResponse.getState());
                    customer.setZipCode(addressResponse.getZipCode());
                    customer.setPhone(customer.getPhone().replace("XX", addressResponse.getDdd()));
                });

        Optional.ofNullable(orderRequest.getPayments())
                .orElse(emptyList())
                .forEach(payment -> payment.setPaymentDate(new VirtualWatchService().nowLocalDateTime()));

        return orderRequest;
    }

    public static void createOrderRequest(OrderRequestDataTable orderRequestDataTable) {
        String json = ObjectMapperHelper.generateJson(orderRequestDataTable);
        orderRequest = ObjectMapperHelper.generateOrderRequest(json);
    }

    public static void resetOrderRequest() {
        orderRequest = OrderRequest.builder()
                .customer(Customer.builder().build())
                .items(emptyList())
                .payments(emptyList())
                .build();
    }
}