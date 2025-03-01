package br.com.alc.ecommerce.channel.core.service.generator.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("java:S5786") // Public required for JUnit test suite
@ExtendWith(SpringExtension.class)
public class ZipCodeGeneratorServiceImplTest {

    @Test
    void whenExecutingTheZipCodeGeneratorThenShouldReturnTheAnyExpectedZipCode() {
        List<String> zipCodeExpecteds = Arrays.asList("01001000", "20040000", "30130000", "60060000", "70040010", "80010000", "90010000",
                "50010000", "57020000", "69005070", "66010000", "79002000", "88010400", "64000020", "58010000", "59010000",
                "76801000", "69301000", "69900120");

        String zipCodeReturned = new ZipCodeGeneratorServiceImpl().execute();

        assertTrue(zipCodeExpecteds.contains(zipCodeReturned), "Should return the any expected zipcode");
    }
}