package br.com.alc.ecommerce.channel.infrastructure.web.handler;

import br.com.alc.ecommerce.channel.core.exception.OrderNotFoundException;
import br.com.alc.ecommerce.channel.core.exception.PeriodInvalidException;
import br.com.alc.ecommerce.channel.infrastructure.dto.error.ErrorResponseDto;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

@ControllerAdvice
@ConditionalOnProperty(name = "app.handle.enable", havingValue = "true", matchIfMissing = true)
public class EcommerceChannelExceptionHandler extends AbstractExceptionHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<ErrorResponseDto>> handleWebExchangeBindException(WebExchangeBindException exception) {
        return super.handleGenericMethodArgumentNotValidException(exception.getBindingResult());
    }

    @Override
    @ExceptionHandler(OrderNotFoundException.class)
    public Mono<ResponseEntity<ErrorResponseDto>> handleOrderNotFoundException(OrderNotFoundException exception) {
        return super.handleOrderNotFoundException(exception);
    }

    @Override
    @ExceptionHandler(PeriodInvalidException.class)
    public Mono<ResponseEntity<ErrorResponseDto>> handlePeriodInvalidException(PeriodInvalidException exception) {
        return super.handlePeriodInvalidException(exception);
    }
}