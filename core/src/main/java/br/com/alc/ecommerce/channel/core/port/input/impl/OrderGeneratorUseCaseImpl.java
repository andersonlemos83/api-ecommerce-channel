package br.com.alc.ecommerce.channel.core.port.input.impl;

import br.com.alc.ecommerce.channel.core.domain.generator.OrderGeneratorRequest;
import br.com.alc.ecommerce.channel.core.domain.order.OrderRequest;
import br.com.alc.ecommerce.channel.core.port.input.OrderGeneratorUseCase;
import br.com.alc.ecommerce.channel.core.port.output.OrderIntegratorOutPort;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil.generateJson;

@Log4j2
@AllArgsConstructor
public class OrderGeneratorUseCaseImpl implements OrderGeneratorUseCase {

    private final OrderIntegratorOutPort orderIntegratorOutPort;

    @Override
    public void execute(OrderGeneratorRequest orderGeneratorRequest) {
        log.info("Incoming into OrderGeneratorUseCaseImpl: {}", generateJson(orderGeneratorRequest));
        OrderRequest orderRequest = OrderRequest.builder()
                .channelCode(buildRandomChannelCode())
                .companyCode(buildRandomCompanyCode())
                .storeCode(buildRandomStoreCode())
                .pos(buildRandomPos())
                .orderNumber(orderGeneratorRequest.getOrderNumber())
                .build();
        orderIntegratorOutPort.execute(orderRequest);
    }

    private String buildRandomChannelCode() {
        List<String> channels = Arrays.asList("WEB", "APP", "STR", "SLF");
        int randomIndex = ThreadLocalRandom.current().nextInt(channels.size());
        return channels.get(randomIndex);
    }

    private String buildRandomCompanyCode() {
        int randomCompanyCode = new Random().nextInt(999);
        return StringUtils.leftPad(String.valueOf(randomCompanyCode), 3, '0');
    }

    private String buildRandomStoreCode() {
        int randomStoreCode = new Random().nextInt(999);
        return StringUtils.leftPad(String.valueOf(randomStoreCode), 3, '0');
    }

    private Integer buildRandomPos() {
        return new Random().nextInt(999);
    }
}