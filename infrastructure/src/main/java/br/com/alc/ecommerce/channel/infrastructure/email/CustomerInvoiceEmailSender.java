package br.com.alc.ecommerce.channel.infrastructure.email;

import br.com.alc.ecommerce.channel.infrastructure.dto.customerinvoice.CustomerInvoiceRequestDto;

public interface CustomerInvoiceEmailSender {

    void send(CustomerInvoiceRequestDto customerInvoiceRequestDto);

}