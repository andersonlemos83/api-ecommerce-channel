package br.com.alc.ecommerce.channel.core.port.input.impl;

import br.com.alc.ecommerce.channel.core.domain.order.Order;
import br.com.alc.ecommerce.channel.core.domain.order.OrderRequest;
import br.com.alc.ecommerce.channel.core.port.input.OrderProcessorUseCase;
import br.com.alc.ecommerce.channel.core.port.output.OrderInserterOutPort;
import br.com.alc.ecommerce.channel.core.port.output.OrderInvoicerOutPort;
import br.com.alc.ecommerce.channel.core.service.watch.WatchService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;
import java.util.Optional;

import static br.com.alc.ecommerce.channel.core.domain.order.OrderStatus.*;

@Log4j2
@AllArgsConstructor
public final class OrderProcessorUseCaseImpl implements OrderProcessorUseCase {

    private final OrderInserterOutPort orderInserterOutPort;
    private final OrderInvoicerOutPort orderInvoicerOutPort;
    private final WatchService watchService;

    @Override
    public void execute(OrderRequest orderRequest) {
        Order createdOrder = buildCreatedOrder(orderRequest);
        orderInserterOutPort.execute(createdOrder);
        Optional<String> responseOptinal = orderInvoicerOutPort.execute(orderRequest);

        if (responseOptinal.isPresent()) {
            Order errorOrder = buildErrorOrder(orderRequest, responseOptinal.get());
            orderInserterOutPort.execute(errorOrder);
            return;
        }

        Order invoicePendingOrder = buildInvoicePendingOrder(orderRequest);
        orderInserterOutPort.execute(invoicePendingOrder);
    }

    private Order buildCreatedOrder(OrderRequest orderRequest) {
        Order.OrderBuilder orderBuilder = buildOrderBuilder(orderRequest);
        return orderBuilder
                .status(CREATED)
                .errorReason(null)
                .build();
    }

    private Order buildInvoicePendingOrder(OrderRequest orderRequest) {
        Order.OrderBuilder orderBuilder = buildOrderBuilder(orderRequest);
        return orderBuilder
                .status(INVOICE_PENDING)
                .errorReason(null)
                .build();
    }

    private Order buildErrorOrder(OrderRequest orderRequest, String errorReason) {
        Order.OrderBuilder orderBuilder = buildOrderBuilder(orderRequest);
        return orderBuilder
                .status(ERROR)
                .errorReason(errorReason)
                .build();
    }

    private Order.OrderBuilder buildOrderBuilder(OrderRequest orderRequest) {
        LocalDateTime now = watchService.nowLocalDateTime();
        return Order.builder()
                .orderRequest(orderRequest)
                .createdDate(now)
                .updatedDate(now);
    }
}