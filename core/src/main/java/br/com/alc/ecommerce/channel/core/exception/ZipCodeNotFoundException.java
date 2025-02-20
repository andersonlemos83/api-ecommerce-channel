package br.com.alc.ecommerce.channel.core.exception;

import java.text.MessageFormat;

public final class ZipCodeNotFoundException extends RuntimeException {

    public static final String ZIP_CODE_MESSAGE_PATTERN = "O CEP {0} não foi encontrado.";

    public ZipCodeNotFoundException(String cep) {
        super(MessageFormat.format(ZIP_CODE_MESSAGE_PATTERN, cep));
    }
}