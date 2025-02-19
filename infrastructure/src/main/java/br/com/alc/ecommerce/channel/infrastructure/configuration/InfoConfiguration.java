package br.com.alc.ecommerce.channel.infrastructure.configuration;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
@AllArgsConstructor
public class InfoConfiguration {

    private final GitProperties gitProperties;
    private final BuildProperties buildProperties;

    @PostConstruct
    void init() {
        log.info("API: {}; Version: {}; Branch: {}; CommitId: {}", buildProperties.getArtifact(),
                buildProperties.getVersion(), gitProperties.getBranch(), gitProperties.getCommitId());
    }
}