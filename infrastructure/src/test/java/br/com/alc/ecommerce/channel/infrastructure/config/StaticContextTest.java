package br.com.alc.ecommerce.channel.infrastructure.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.TimeZone;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("java:S5786") // Public required for JUnit test suite
@ExtendWith(SpringExtension.class)
public class StaticContextTest {

    @Test
    void initializeStaticContext() {
        System.out.println("TimeZone Default: " + TimeZone.getDefault());
        TimeZone.setDefault(TimeZone.getTimeZone("America/Fortaleza"));
        System.out.println("TimeZone Atualizado: " + TimeZone.getDefault());

        assertEquals("America/Fortaleza", TimeZone.getDefault().getID());
    }
}