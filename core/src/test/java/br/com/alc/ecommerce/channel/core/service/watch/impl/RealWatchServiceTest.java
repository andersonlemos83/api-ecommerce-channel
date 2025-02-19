package br.com.alc.ecommerce.channel.core.service.watch.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("java:S5786") // Public required for JUnit test suite
@ExtendWith(SpringExtension.class)
public class RealWatchServiceTest {

    @Test
    void whenExecutingTheNowLocalDateTimeMethodThenShouldReturnTheExpectedCurrentLocalDateTime() {
        LocalDateTime expected = LocalDateTime.now();
        LocalDateTime returned = new RealWatchService().nowLocalDateTime();

        boolean acceptableRange = expected.minusSeconds(1).isBefore(returned) && expected.plusSeconds(1).isAfter(returned);
        assertTrue(acceptableRange, "The returned time is not within an acceptable range");
    }
}