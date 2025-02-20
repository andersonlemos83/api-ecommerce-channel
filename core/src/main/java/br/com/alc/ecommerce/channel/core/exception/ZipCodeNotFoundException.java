package br.com.alc.ecommerce.channel.core.exception;

import java.text.MessageFormat;

public class ZipCodeNotFoundException extends RuntimeException {

    public static final String ZIP_CODE_MESSAGE_PATTERN = "O CEP {} não foi encontrado.";

    public ZipCodeNotFoundException(String cep) {
        super(MessageFormat.format(ZIP_CODE_MESSAGE_PATTERN, cep));
    }
}