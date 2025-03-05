package br.com.alc.ecommerce.channel.infrastructure.web.handler;

import br.com.alc.ecommerce.channel.core.exception.OrderNotFoundException;
import br.com.alc.ecommerce.channel.core.exception.PeriodInvalidException;
import br.com.alc.ecommerce.channel.infrastructure.dto.error.ErrorResponseDto;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice
@ConditionalOnProperty(name = "app.handle.enable", havingValue = "false")
public class SynchronousExceptionHandler extends AbstractExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Mono<ResponseEntity<ErrorResponseDto>> response = super.handleGenericMethodArgumentNotValidException(exception.getBindingResult());
        return response.block();
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleOrderNotFoundException(OrderNotFoundException exception) {
        Mono<ResponseEntity<ErrorResponseDto>> response = super.handleOrderNotFoundException(exception);
        return response.block();
    }

    @ExceptionHandler(PeriodInvalidException.class)
    public ResponseEntity<ErrorResponseDto> handlePeriodInvalidException(PeriodInvalidException exception) {
        Mono<ResponseEntity<ErrorResponseDto>> response = super.handlePeriodInvalidException(exception);
        return response.block();
    }
}