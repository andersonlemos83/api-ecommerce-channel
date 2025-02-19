package br.com.alc.ecommerce.channel.infrastructure.configuration;

import io.micrometer.core.instrument.binder.jvm.*;
import io.micrometer.core.instrument.binder.logging.Log4j2Metrics;
import io.micrometer.core.instrument.binder.system.FileDescriptorMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.core.instrument.binder.system.UptimeMetrics;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MonitoringConfiguration {

    @Bean
    JvmGcMetrics jvmGcMetrics() {
        return new JvmGcMetrics();
    }

    @Bean
    JvmHeapPressureMetrics jvmHeapPressureMetrics() {
        return new JvmHeapPressureMetrics();
    }

    @Bean
    JvmInfoMetrics jvmInfoMetrics() {
        return new JvmInfoMetrics();
    }

    @Bean
    JvmMemoryMetrics jvmMemoryMetrics() {
        return new JvmMemoryMetrics();
    }

    @Bean
    JvmThreadMetrics jvmThreadMetrics() {
        return new JvmThreadMetrics();
    }

    @Bean
    FileDescriptorMetrics fileDescriptorMetrics() {
        return new FileDescriptorMetrics();
    }

    @Bean
    ProcessorMetrics processorMetrics() {
        return new ProcessorMetrics();
    }

    @Bean
    UptimeMetrics uptimeMetrics() {
        return new UptimeMetrics();
    }

    @Bean
    Log4j2Metrics logbackMetrics() {
        return new Log4j2Metrics();
    }
}