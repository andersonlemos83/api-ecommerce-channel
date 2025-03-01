package br.com.alc.ecommerce.channel.core.port.input.impl;

import br.com.alc.ecommerce.channel.core.domain.callback.OrderCallbackRequest;
import br.com.alc.ecommerce.channel.core.domain.order.Order;
import br.com.alc.ecommerce.channel.core.domain.order.OrderStatus;
import br.com.alc.ecommerce.channel.core.domain.order.SaleStatus;
import br.com.alc.ecommerce.channel.core.port.input.OrderCallbackProcessorUseCase;
import br.com.alc.ecommerce.channel.core.port.output.MostRecentOrderFinderOutPort;
import br.com.alc.ecommerce.channel.core.port.output.OrderInserterOutPort;
import br.com.alc.ecommerce.channel.core.service.watch.WatchService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

import static br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil.generateJson;

@Log4j2
@AllArgsConstructor
public final class OrderCallbackProcessorUseCaseImpl implements OrderCallbackProcessorUseCase {

    private static final String OUTGOING_TEMPLATE = "Outgoing from OrderCallbackProcessorUseCaseImpl: {} - {}";

    private static final Map<SaleStatus, OrderStatus> status;

    static {
        status = new EnumMap<>(SaleStatus.class);
        status.put(SaleStatus.ERROR, OrderStatus.ERROR);
        status.put(SaleStatus.PROCESSED, OrderStatus.INVOICED);
    }

    private final MostRecentOrderFinderOutPort mostRecentOrderFinderOutPort;
    private final OrderInserterOutPort orderInserterOutPort;
    private final WatchService watchService;

    @Override
    public void execute(OrderCallbackRequest orderCallbackRequest) {
        log.info("Incoming into OrderCallbackProcessorUseCaseImpl: {}", generateJson(orderCallbackRequest));
        Optional<Order> orderOptional = mostRecentOrderFinderOutPort.execute(orderCallbackRequest.getOrderNumber());

        if (orderOptional.isEmpty() || orderCallbackRequest.isNotProcessedOrNotError()) {
            log.info(OUTGOING_TEMPLATE, orderCallbackRequest.getStatus(), generateJson(orderCallbackRequest));
            return;
        }

        Order order = buildUpdatedOrder(orderCallbackRequest, orderOptional.get());
        orderInserterOutPort.execute(order);
        log.info(OUTGOING_TEMPLATE, order.getStatus(), generateJson(order));
    }

    private Order buildUpdatedOrder(OrderCallbackRequest orderCallbackRequest, Order order) {
        order.setInvoiceKey(orderCallbackRequest.getInvoiceKey());
        order.setInvoiceNumber(orderCallbackRequest.getInvoiceNumber());
        order.setIssuanceDate(orderCallbackRequest.getIssuanceDate());
        order.setInvoiceBase64(orderCallbackRequest.getInvoiceBase64());
        order.setStatus(status.get(orderCallbackRequest.getStatus()));
        order.setErrorReason(orderCallbackRequest.getErrorReason());
        order.setUpdatedDate(watchService.nowLocalDateTime());
        return order;
    }
}