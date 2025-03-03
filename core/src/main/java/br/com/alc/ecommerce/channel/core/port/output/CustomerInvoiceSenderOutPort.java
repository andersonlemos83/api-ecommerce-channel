package br.com.alc.ecommerce.channel.core.port.output;

import br.com.alc.ecommerce.channel.core.domain.customerinvoice.CustomerInvoiceRequest;

public interface CustomerInvoiceSenderOutPort {

    void execute(CustomerInvoiceRequest customerInvoiceRequest);

}