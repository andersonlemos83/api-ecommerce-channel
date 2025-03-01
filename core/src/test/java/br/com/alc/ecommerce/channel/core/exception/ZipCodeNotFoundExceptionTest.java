package br.com.alc.ecommerce.channel.core.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("java:S5786") // Public required for JUnit test suite
@ExtendWith(SpringExtension.class)
public class ZipCodeNotFoundExceptionTest {

    @Test
    void givenAnValidZipCodeNotFoundExceptionWhenExecutingTheGetMessageMethodThenReturnTheExpectedMessage() {
        ZipCodeNotFoundException zipCodeNotFoundException = new ZipCodeNotFoundException("57048434");
        assertEquals("O CEP 57048434 não foi encontrado.", zipCodeNotFoundException.getMessage());
    }
}