package br.com.alc.ecommerce.channel.core.exception;

public final class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException() {
        super("O pedido não foi encontrado.");
    }
}