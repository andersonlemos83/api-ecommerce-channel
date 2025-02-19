package br.com.alc.ecommerce.channel.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EntityScan(basePackages = {"br.com.alc.ecommerce.channel.infrastructure.*"})
@SpringBootApplication(scanBasePackages = {"br.com.alc.ecommerce.channel.infrastructure"})
public class EcommerceChannelInfrastructureApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceChannelInfrastructureApplication.class, args);
    }

}