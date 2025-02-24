package br.com.alc.ecommerce.channel.infrastructure.web.handler;

import br.com.alc.ecommerce.channel.core.exception.OrderNotFoundException;
import br.com.alc.ecommerce.channel.infrastructure.dto.error.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;
import java.util.Objects;

import static java.util.Comparator.naturalOrder;
import static java.util.stream.Collectors.joining;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class EcommerceChannelExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleOrderNotFoundException(OrderNotFoundException exception) {
        return Mono.just(exception)
                .map(OrderNotFoundException::getMessage)
                .map(this::buildBadRequestErrorResponseDto)
                .map(this::buildBadRequestResponseEntity)
                .block();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return Mono.just(Objects.requireNonNull(exception.getBindingResult()))
                .map(this::joiningMessages)
                .map(this::buildBadRequestErrorResponseDto)
                .map(this::buildBadRequestResponseEntity)
                .block();
    }

    private String joiningMessages(BindingResult bindingResult) {
        return bindingResult
                .getFieldErrors()
                .stream()
                .map(this::buildMessage)
                .sorted(naturalOrder())
                .distinct()
                .collect(joining(", ", "", "."));
    }

    private String buildMessage(FieldError fieldError) {
        return MessageFormat.format("O campo {0} {1}", fieldError.getField(), fieldError.getDefaultMessage());
    }

    private ErrorResponseDto buildBadRequestErrorResponseDto(String message) {
        return ErrorResponseDto.builder()
                .httpStatus(BAD_REQUEST)
                .message(message)
                .build();
    }

    private ResponseEntity<ErrorResponseDto> buildBadRequestResponseEntity(ErrorResponseDto errorResponseDto) {
        return ResponseEntity.status(BAD_REQUEST).body(errorResponseDto);
    }
}