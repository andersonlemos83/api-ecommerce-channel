package br.com.alc.ecommerce.channel.core.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("java:S5786") // Public required for JUnit test suite
@ExtendWith(SpringExtension.class)
public class DefaultOutPortExceptionTest {

    @Test
    void givenAnValidDefaultOutPortExceptionWhenExecutingTheGetMessageMethodThenReturnTheExpectedMessage() {
        DefaultOutPortException defaultOutPortException = new DefaultOutPortException("Imposto não encontrado.", new RuntimeException());
        assertEquals("Imposto não encontrado.", defaultOutPortException.getMessage());
    }
}