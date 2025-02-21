package br.com.alc.ecommerce.channel.infrastructure.web.handler;

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