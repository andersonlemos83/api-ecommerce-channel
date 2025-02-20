package br.com.alc.ecommerce.channel.infrastructure.web.handler;

import br.com.alc.ecommerce.channel.infrastructure.dto.error.ErrorResponseDto;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.MessageFormat;

import static java.util.Comparator.naturalOrder;
import static java.util.stream.Collectors.joining;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

//@RestControllerAdvice
//@Order(HIGHEST_PRECEDENCE)
//public class EcommerceChannelExceptionHandler extends ResponseEntityExceptionHandler {
public class EcommerceChannelExceptionHandler {

//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        String message = ex.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .map(this::buildMessage)
//                .sorted(naturalOrder())
//                .distinct()
//                .collect(joining(", ", "", "."));
//        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
//                .httpStatus((HttpStatus) status)
//                .message(message)
//                .build();
//        return handleExceptionInternal(ex, errorResponseDto, new HttpHeaders(), status, request);
//    }
//
//    private String buildMessage(FieldError fieldError) {
//        return MessageFormat.format("O campo {0} {1}", fieldError.getField(), fieldError.getDefaultMessage());
//    }
}