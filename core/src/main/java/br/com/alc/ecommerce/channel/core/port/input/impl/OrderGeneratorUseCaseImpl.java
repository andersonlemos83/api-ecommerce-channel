package br.com.alc.ecommerce.channel.core.port.input.impl;

import br.com.alc.ecommerce.channel.core.domain.address.AddressResponse;
import br.com.alc.ecommerce.channel.core.domain.generator.OrderGeneratorRequest;
import br.com.alc.ecommerce.channel.core.domain.order.OrderRequest;
import br.com.alc.ecommerce.channel.core.port.input.OrderGeneratorUseCase;
import br.com.alc.ecommerce.channel.core.port.output.AddressFinderOutPort;
import br.com.alc.ecommerce.channel.core.port.output.OrderIntegratorOutPort;
import br.com.alc.ecommerce.channel.core.service.generator.CepGeneratorService;
import br.com.alc.ecommerce.channel.core.service.generator.OrderGeneratorService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import static br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil.generateJson;

@Log4j2
@AllArgsConstructor
public final class OrderGeneratorUseCaseImpl implements OrderGeneratorUseCase {

    private final CepGeneratorService cepGeneratorService;
    private final AddressFinderOutPort addressFinderOutPort;
    private final OrderGeneratorService orderGeneratorService;
    private final OrderIntegratorOutPort orderIntegratorOutPort;

    @Override
    public void execute(OrderGeneratorRequest orderGeneratorRequest) {
        log.info("Incoming into OrderGeneratorUseCaseImpl: {}", generateJson(orderGeneratorRequest));
        String cep = cepGeneratorService.execute();
        AddressResponse addressResponse = addressFinderOutPort.execute(cep);
        OrderRequest orderRequest = orderGeneratorService.execute(orderGeneratorRequest, addressResponse);
        orderIntegratorOutPort.execute(orderRequest);
    }
}