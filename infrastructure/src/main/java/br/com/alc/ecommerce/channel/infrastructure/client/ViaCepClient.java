package br.com.alc.ecommerce.channel.infrastructure.client;

import br.com.alc.ecommerce.channel.infrastructure.dto.location.AddressResponseDto;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = "via-cep", url = "${client.via-cep.url}")
public interface ViaCepClient {

    @Headers("Content-Type: " + APPLICATION_JSON_VALUE)
    @GetMapping(value = "/ws/{cep}/json/", consumes = APPLICATION_JSON_VALUE)
    AddressResponseDto findByCep(@PathVariable("cep") String cep);

}