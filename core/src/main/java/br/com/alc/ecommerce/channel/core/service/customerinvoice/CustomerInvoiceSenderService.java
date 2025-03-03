package br.com.alc.ecommerce.channel.core.service.customerinvoice;

import br.com.alc.ecommerce.channel.core.domain.order.Order;

public interface CustomerInvoiceSenderService {

    void execute(Order order);

}