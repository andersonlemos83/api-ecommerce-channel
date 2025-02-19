package br.com.alc.ecommerce.channel.core.service.watch.impl;

import br.com.alc.ecommerce.channel.core.service.watch.WatchService;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;

import static br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil.generateJson;

@Log4j2
public final class RealWatchService implements WatchService {

    @Override
    public LocalDateTime nowLocalDateTime() {
        LocalDateTime now = LocalDateTime.now();
        log.info("Outgoing from RealWatchService: {}", generateJson(now));
        return now;
    }
}