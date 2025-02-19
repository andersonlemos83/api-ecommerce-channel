package br.com.alc.ecommerce.channel.infrastructure.adapter.input;

import br.com.alc.ecommerce.channel.infrastructure.dto.generator.OrderGeneratorRequestDto;

public interface OrderGeneratorInAdapter {

    void execute(OrderGeneratorRequestDto orderGeneratorRequestDto);

}