package br.com.alc.ecommerce.channel.core.service.generator.stub;

import br.com.alc.ecommerce.channel.core.service.generator.ZipCodeGeneratorService;

public class ZipCodeGeneratorServiceStub implements ZipCodeGeneratorService {

    private static String cepEsperado;

    static {
        resetCep();
    }

    @Override
    public String execute() {
        return cepEsperado;
    }

    public static void createCep(String cep) {
        cepEsperado = cep;
    }

    public static void resetCep() {
        cepEsperado = "987654321";
    }
}