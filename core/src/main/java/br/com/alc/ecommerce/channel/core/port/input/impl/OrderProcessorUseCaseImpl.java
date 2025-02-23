package br.com.alc.ecommerce.channel.core.port.input.impl;

import br.com.alc.ecommerce.channel.core.domain.order.Order;
import br.com.alc.ecommerce.channel.core.domain.order.OrderRequest;
import br.com.alc.ecommerce.channel.core.domain.order.OrderResponse;
import br.com.alc.ecommerce.channel.core.exception.InvoiceNotIssuedException;
import br.com.alc.ecommerce.channel.core.port.input.OrderProcessorUseCase;
import br.com.alc.ecommerce.channel.core.port.output.MostRecentOrderFinderOutPort;
import br.com.alc.ecommerce.channel.core.port.output.OrderInserterOutPort;
import br.com.alc.ecommerce.channel.core.port.output.OrderInvoicerOutPort;
import br.com.alc.ecommerce.channel.core.service.watch.WatchService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;
import java.util.Optional;

import static br.com.alc.ecommerce.channel.core.domain.order.OrderStatus.*;
import static br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil.generateJson;
import static org.apache.commons.lang3.exception.ExceptionUtils.getMessage;

@Log4j2
@AllArgsConstructor
public final class OrderProcessorUseCaseImpl implements OrderProcessorUseCase {

    private static final String OUTGOING_TEMPLATE = "Outgoing from OrderProcessorUseCaseImpl: {} - {}";

    private final MostRecentOrderFinderOutPort mostRecentOrderFinderOutPort;
    private final OrderInserterOutPort orderInserterOutPort;
    private final OrderInvoicerOutPort orderInvoicerOutPort;
    private final WatchService watchService;

    @Override
    public void execute(OrderRequest orderRequest) {
        log.info("Incoming into OrderProcessorUseCaseImpl: {}", generateJson(orderRequest));
        Optional<Order> orderOptional = mostRecentOrderFinderOutPort.execute(orderRequest.getOrderNumber());

        if (orderOptional.filter(Order::isInvoiced).isPresent()) {
            log.info(OUTGOING_TEMPLATE, orderOptional.get().getStatus(), generateJson(orderOptional.get()));
            return;
        }

        try {
            Order createdOrder = buildCreatedOrder(orderRequest);
            orderInserterOutPort.execute(createdOrder);
            OrderResponse orderResponse = orderInvoicerOutPort.execute(orderRequest);
            if (orderResponse.isError()) {
                throw new InvoiceNotIssuedException();
            }
            Order invoicePendingOrder = buildInvoicePendingOrder(orderRequest);
            orderInserterOutPort.execute(invoicePendingOrder);
            log.info(OUTGOING_TEMPLATE, invoicePendingOrder.getStatus(), generateJson(invoicePendingOrder));
        } catch (Exception exception) {
            log.error("Error in the OrderProcessorUseCaseImpl: {}", getMessage(exception), exception);
            Order errorOrder = buildErrorOrder(orderRequest, exception.getMessage());
            orderInserterOutPort.execute(errorOrder);
            log.info(OUTGOING_TEMPLATE, errorOrder.getStatus(), generateJson(errorOrder));
        }
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