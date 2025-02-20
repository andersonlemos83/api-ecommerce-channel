package br.com.alc.ecommerce.channel.infrastructure.adapter.output;

import br.com.alc.ecommerce.channel.core.domain.location.AddressResponse;
import br.com.alc.ecommerce.channel.core.exception.DefaultOutPortException;
import br.com.alc.ecommerce.channel.core.exception.ZipCodeNotFoundException;
import br.com.alc.ecommerce.channel.core.port.output.AddressFinderOutPort;
import br.com.alc.ecommerce.channel.infrastructure.client.ViaCepClient;
import br.com.alc.ecommerce.channel.infrastructure.dto.location.AddressResponseDto;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import static br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil.generateJson;
import static br.com.alc.ecommerce.channel.infrastructure.util.ConstantesUtil.ADDRESS_FINDER_CACHE;
import static org.apache.commons.lang3.exception.ExceptionUtils.getMessage;

@Log4j2
@Component
@AllArgsConstructor
public class AddressFinderOutPortImpl implements AddressFinderOutPort {

    private final RetryTemplate retryTemplate;
    private final ViaCepClient viaCepClient;
    private final ModelMapper modelMapper;

    @Override
    @Cacheable(cacheNames = ADDRESS_FINDER_CACHE, key = "#cep", unless = "#result == null")
    public AddressResponse execute(String cep) {
        AddressResponse addressResponse = findAddressByZipCodeWithRetry(cep);
        if (addressResponse.isErro()) {
            throw new ZipCodeNotFoundException(cep);
        }
        return addressResponse;
    }

    private AddressResponse findAddressByZipCodeWithRetry(String cep) {
        try {
            log.debug("Incoming into AddressFinderOutPortImpl: {}", generateJson(cep));
            return retryTemplate.execute(callback -> {
                log.info("---> Request GET /ws/{}/json/ {}: {}", cep, callback.getRetryCount() + 1, cep);
                AddressResponseDto addressResponseDto = viaCepClient.findByCep(cep);
                log.info("<--- Response GET /ws/{}/json/: {}", cep, generateJson(addressResponseDto));
                log.info("Save cache {}:{} - {}", ADDRESS_FINDER_CACHE, cep, generateJson(addressResponseDto));
                AddressResponse addressResponse = modelMapper.map(addressResponseDto, AddressResponse.class);
                log.debug("Outgoing from AddressFinderOutPortImpl: {}", generateJson(addressResponse));
                return addressResponse;
            });
        } catch (FeignException exception) {
            log.error("Error in the AddressFinderOutPortImpl: {}", getMessage(exception), exception);
            throw new DefaultOutPortException(exception.contentUTF8(), exception.getCause());
        }
    }
}