package br.com.alc.ecommerce.channel.infrastructure.client;

import br.com.alc.ecommerce.channel.infrastructure.dto.address.AddressResponseDto;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = "api-via-cep", url = "${client.via-cep.url}")
public interface ViaCepClient {

    @Headers("Content-Type: " + APPLICATION_JSON_VALUE)
    @GetMapping(value = "/ws/{zipCode}/json/", consumes = APPLICATION_JSON_VALUE)
    AddressResponseDto findByZipCode(@PathVariable("zipCode") String zipCode);

}