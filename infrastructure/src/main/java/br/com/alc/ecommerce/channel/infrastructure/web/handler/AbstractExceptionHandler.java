package br.com.alc.ecommerce.channel.infrastructure.web.handler;

import br.com.alc.ecommerce.channel.core.exception.OrderNotFoundException;
import br.com.alc.ecommerce.channel.core.exception.PeriodInvalidException;
import br.com.alc.ecommerce.channel.infrastructure.dto.error.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;

import static java.util.Comparator.naturalOrder;
import static java.util.stream.Collectors.joining;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public abstract class AbstractExceptionHandler {

    protected <T> T handleGenericMethodArgumentNotValidException(BindingResult bindingResult) {
        return (T) Mono.just(bindingResult)
                .map(this::joiningMessages)
                .map(this::buildBadRequestErrorResponseDto)
                .map(this::buildBadRequestResponseEntity);
    }

    protected <T> T handleOrderNotFoundException(OrderNotFoundException exception) {
        return (T) handeBusinessException(exception);
    }

    protected <T> T handlePeriodInvalidException(PeriodInvalidException exception) {
        return (T) handeBusinessException(exception);
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

    private Mono<ResponseEntity<ErrorResponseDto>> handeBusinessException(Exception exception) {
        return Mono.just(exception)
                .map(Exception::getMessage)
                .map(this::buildBadRequestErrorResponseDto)
                .map(this::buildBadRequestResponseEntity);
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