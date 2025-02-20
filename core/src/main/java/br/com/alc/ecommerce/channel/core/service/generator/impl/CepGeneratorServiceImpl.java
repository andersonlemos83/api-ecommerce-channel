package br.com.alc.ecommerce.channel.core.service.generator.impl;

import br.com.alc.ecommerce.channel.core.service.generator.CepGeneratorService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.ThreadLocalRandom;

import static br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil.generateJson;

@Log4j2
@AllArgsConstructor
public final class CepGeneratorServiceImpl implements CepGeneratorService {

    private static final String[] CEPS = new String[]{
            "01001000", // Praça da Sé, São Paulo/SP
            "20040000", // Avenida Rio Branco, Rio de Janeiro/RJ
            "30130000", // Praça Sete de Setembro, Belo Horizonte/MG
            "60060000", // Rua Barão do Rio Branco, Fortaleza/CE
            "70040010", // Esplanada dos Ministérios, Brasília/DF
            "80010000", // Praça Tiradentes, Curitiba/PR
            "90010000", // Rua dos Andradas, Porto Alegre/RS
            "50010000", // Rua do Imperador, Recife/PE
            "57020000", // Avenida da Paz, Maceió/AL
            "69005070", // Rua Guilherme Moreira, Manaus/AM
            "66010000", // Avenida Presidente Vargas, Belém/PA
            "79002000", // Rua 14 de Julho, Campo Grande/MS
            "88010400", // Rua Felipe Schmidt, Florianópolis/SC
            "64000020", // Avenida Frei Serafim, Teresina, PI
            "58010000", // Avenida Getúlio Vargas, João Pessoa/PB
            "59010000", // Avenida Rio Branco, Natal/RN
            "76801000", // Avenida Sete de Setembro, Porto Velho/RO
            "69301000", // Avenida Capitão Ene Garcez, Boa Vista/RR
            "69900120"  // Rua Rui Barbosa, Centro, Rio Branco/AC
    };

    @Override
    public String execute() {
        int index = ThreadLocalRandom.current().nextInt(CEPS.length);
        String cep = CEPS[index];
        log.info("Outgoing from CepGeneratorServiceImpl: {}", generateJson(cep));
        return cep;
    }
}