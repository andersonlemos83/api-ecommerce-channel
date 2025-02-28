package br.com.alc.ecommerce.channel.core.service.validator.impl;

import br.com.alc.ecommerce.channel.core.domain.finder.OrderFinderRequest;
import br.com.alc.ecommerce.channel.core.exception.PeriodInvalidException;
import br.com.alc.ecommerce.channel.core.service.validator.ByPeriodOrderFinderValidatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("java:S5786") // Public required for JUnit test suite
@ExtendWith(SpringExtension.class)
public class ByPeriodOrderFinderValidatorServiceImplTest {

    private ByPeriodOrderFinderValidatorService validator;

    @BeforeEach
    public void setUp() {
        validator = new ByPeriodOrderFinderValidatorServiceImpl();
    }

    @Test
    void givenAnInvalidPeriodoWhenExecutingTheByPeriodOrderFinderValidatorThenShouldThrowsAhPeriodInvalidException() {
        OrderFinderRequest orderFinderRequest = buildInvalidOrderFinderRequest();
        PeriodInvalidException exception = assertThrows(PeriodInvalidException.class, () -> validator.validate(orderFinderRequest).block());
        assertEquals("O período de 30/01/2025 00:00:00 até 29/01/2025 23:59:59 é inválido.", exception.getMessage());
    }

    @Test
    void givenAnValidPeriodoWhenExecutingTheByPeriodOrderFinderValidatorThenNoShouldThrowsAhPeriodInvalidException() {
        OrderFinderRequest orderFinderRequest = buildValidOrderFinderRequest();
        Optional<Void> optionalReturned = validator.validate(orderFinderRequest).blockOptional();
        assertTrue(optionalReturned.isEmpty());
    }

    private OrderFinderRequest buildInvalidOrderFinderRequest() {
        return OrderFinderRequest.builder()
                .startPeriodDate(LocalDate.now()
                        .withYear(2025)
                        .withMonth(01)
                        .withDayOfMonth(30))
                .endPeriodDate(LocalDate.now()
                        .withYear(2025)
                        .withMonth(01)
                        .withDayOfMonth(29))
                .build();
    }

    private OrderFinderRequest buildValidOrderFinderRequest() {
        return OrderFinderRequest.builder()
                .startPeriodDate(LocalDate.now()
                        .withYear(2025)
                        .withMonth(01)
                        .withDayOfMonth(29))
                .endPeriodDate(LocalDate.now()
                        .withYear(2025)
                        .withMonth(01)
                        .withDayOfMonth(30))
                .build();
    }
}