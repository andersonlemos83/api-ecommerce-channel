package br.com.alc.ecommerce.channel.infrastructure.config;

import com.icegreen.greenmail.util.GreenMail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.icegreen.greenmail.util.ServerSetupTest.SMTP_POP3;

@Configuration
public class GreenMailConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public GreenMail greenMail() {
        return new GreenMail(SMTP_POP3);
    }
}