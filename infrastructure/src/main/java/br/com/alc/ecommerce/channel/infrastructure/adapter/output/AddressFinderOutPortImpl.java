package br.com.alc.ecommerce.channel.infrastructure.adapter.output;

import br.com.alc.ecommerce.channel.core.domain.address.AddressResponse;
import br.com.alc.ecommerce.channel.core.exception.ZipCodeNotFoundException;
import br.com.alc.ecommerce.channel.core.port.output.AddressFinderOutPort;
import br.com.alc.ecommerce.channel.infrastructure.client.ViaCepClient;
import br.com.alc.ecommerce.channel.infrastructure.dto.address.AddressResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import static br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil.generateJson;
import static br.com.alc.ecommerce.channel.infrastructure.util.ConstantesUtil.ADDRESS_FINDER_CACHE;

@Log4j2
@Component
@AllArgsConstructor
public class AddressFinderOutPortImpl implements AddressFinderOutPort {

    private final RetryTemplate retryTemplate;
    private final ViaCepClient viaCepClient;
    private final ModelMapper modelMapper;

    @Override
    @Cacheable(cacheNames = ADDRESS_FINDER_CACHE, key = "#zipCode", unless = "#result == null")
    public AddressResponse execute(String zipCode) {
        AddressResponse addressResponse = findAddressByZipCodeWithRetry(zipCode);
        if (addressResponse.isErro()) {
            throw new ZipCodeNotFoundException(zipCode);
        }
        return addressResponse;
    }

    private AddressResponse findAddressByZipCodeWithRetry(String zipCode) {
        log.debug("Incoming into AddressFinderOutPortImpl: {}", generateJson(zipCode));
        return retryTemplate.execute(callback -> {
            log.info("---> Request GET /ws/{}/json/ {}: {}", zipCode, callback.getRetryCount() + 1, zipCode);
            AddressResponseDto addressResponseDto = viaCepClient.findByZipCode(zipCode);
            log.info("<--- Response GET /ws/{}/json/: {}", zipCode, generateJson(addressResponseDto));
            log.info("Save cache {}:{} - {}", ADDRESS_FINDER_CACHE, zipCode, generateJson(addressResponseDto));
            AddressResponse addressResponse = modelMapper.map(addressResponseDto, AddressResponse.class);
            log.debug("Outgoing from AddressFinderOutPortImpl: {}", generateJson(addressResponse));
            return addressResponse;
        });
    }
}