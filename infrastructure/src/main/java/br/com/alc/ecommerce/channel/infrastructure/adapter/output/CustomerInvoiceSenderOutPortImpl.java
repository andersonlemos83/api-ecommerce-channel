package br.com.alc.ecommerce.channel.infrastructure.adapter.output;

import br.com.alc.ecommerce.channel.core.domain.customerinvoice.CustomerInvoiceRequest;
import br.com.alc.ecommerce.channel.core.port.output.CustomerInvoiceSenderOutPort;
import br.com.alc.ecommerce.channel.infrastructure.dto.customerinvoice.CustomerInvoiceRequestDto;
import br.com.alc.ecommerce.channel.infrastructure.email.CustomerInvoiceEmailSender;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import static br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil.generateJson;

@Log4j2
@Component
@AllArgsConstructor
public class CustomerInvoiceSenderOutPortImpl implements CustomerInvoiceSenderOutPort {

    private final CustomerInvoiceEmailSender customerInvoiceEmailSender;
    private final ModelMapper modelMapper;

    @Override
    public void execute(CustomerInvoiceRequest customerInvoiceRequest) {
        log.debug("Incoming into CustomerInvoiceSenderOutPortImpl: {}", generateJson(customerInvoiceRequest));
        CustomerInvoiceRequestDto customerInvoiceRequestDto = modelMapper.map(customerInvoiceRequest, CustomerInvoiceRequestDto.class);
        customerInvoiceEmailSender.send(customerInvoiceRequestDto);
    }
}