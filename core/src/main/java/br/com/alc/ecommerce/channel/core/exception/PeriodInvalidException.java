package br.com.alc.ecommerce.channel.core.exception;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class PeriodInvalidException extends RuntimeException {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final String PERIOD_MESSAGE_PATTERN = "O período de {0} até {1} é inválido.";

    public PeriodInvalidException(LocalDateTime from, LocalDateTime to) {
        super(buildMessage(from, to));
    }

    private static String buildMessage(LocalDateTime from, LocalDateTime to) {
        String fromFormated = from.format(DATE_FORMATTER);
        String toFormated = to.format(DATE_FORMATTER);
        return MessageFormat.format(PERIOD_MESSAGE_PATTERN, fromFormated, toFormated);
    }
}