package br.com.alc.ecommerce.channel.core.service.watch.stub;

import br.com.alc.ecommerce.channel.core.service.watch.WatchService;
import br.com.alc.ecommerce.channel.core.service.watch.impl.RealWatchService;
import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.watch.WatchDataTable;

import java.time.LocalDateTime;

public class VirtualWatchService implements WatchService {

    private static LocalDateTime now;

    static {
        resetCurrentTime();
    }

    @Override
    public LocalDateTime nowLocalDateTime() {
        return now;
    }

    public static void createDateAndTime(WatchDataTable watchDataTable) {
        now = LocalDateTime.now()
                .withYear(watchDataTable.getYear())
                .withMonth(watchDataTable.getMonth())
                .withDayOfMonth(watchDataTable.getDay())
                .withHour(watchDataTable.getHour())
                .withMinute(watchDataTable.getMinute())
                .withSecond(watchDataTable.getSecond())
                .withNano(0);
    }

    public static void resetCurrentTime() {
        now = new RealWatchService().nowLocalDateTime();
    }
}