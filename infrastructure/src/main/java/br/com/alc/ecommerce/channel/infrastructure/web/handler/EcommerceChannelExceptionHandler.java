package br.com.alc.ecommerce.channel.infrastructure.web.handler;

import br.com.alc.ecommerce.channel.core.exception.OrderNotFoundException;
import br.com.alc.ecommerce.channel.core.exception.PeriodInvalidException;
import br.com.alc.ecommerce.channel.infrastructure.dto.error.ErrorResponseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;

import static br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil.generateJson;
import static java.util.Comparator.naturalOrder;
import static java.util.stream.Collectors.joining;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Log4j2
@ControllerAdvice
public class EcommerceChannelExceptionHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<ErrorResponseDto>> handleWebExchangeBindException(WebExchangeBindException exception) {
        return Mono.just(exception.getBindingResult())
                .map(this::joiningMessages)
                .map(this::buildBadRequestErrorResponseDto)
                .map(this::buildBadRequestResponseEntity);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public Mono<ResponseEntity<ErrorResponseDto>> handleOrderNotFoundException(OrderNotFoundException exception) {
        return handeBusinessException(exception);
    }

    @ExceptionHandler(PeriodInvalidException.class)
    public Mono<ResponseEntity<ErrorResponseDto>> handlePeriodInvalidException(PeriodInvalidException exception) {
        return handeBusinessException(exception);
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
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .httpStatus(BAD_REQUEST)
                .message(message)
                .build();
        log.debug("Outgoing from AbstractExceptionHandler: {}", generateJson(errorResponseDto));
        return errorResponseDto;
    }

    private ResponseEntity<ErrorResponseDto> buildBadRequestResponseEntity(ErrorResponseDto errorResponseDto) {
        return ResponseEntity.status(BAD_REQUEST).body(errorResponseDto);
    }
}