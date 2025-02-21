package br.com.alc.ecommerce.channel.core.exception;

public final class InvoiceNotIssuedException extends RuntimeException {

    public InvoiceNotIssuedException() {
        super("A nota fiscal não foi emitida.");
    }
}