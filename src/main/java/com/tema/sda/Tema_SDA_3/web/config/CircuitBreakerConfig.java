package com.tema.sda.Tema_SDA_3.web.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.core.registry.EntryAddedEvent;
import io.github.resilience4j.core.registry.EntryRemovedEvent;
import io.github.resilience4j.core.registry.EntryReplacedEvent;
import io.github.resilience4j.core.registry.RegistryEventConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CircuitBreakerConfig {

    @Bean
    public io.github.resilience4j.circuitbreaker.CircuitBreakerConfig getCircuitBreakerConfig() {
        return io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.custom()
                .waitDurationInOpenState(Duration.ofMillis(120000))
                .enableAutomaticTransitionFromOpenToHalfOpen()
                .failureRateThreshold(1)
                .build();
    }

    @Bean
    public CircuitBreaker getConfiguration(io.github.resilience4j.circuitbreaker.CircuitBreakerConfig config) {
        return CircuitBreakerRegistry.of(config, new RegistryEventConsumer<CircuitBreaker>() {
            @Override
            public void onEntryAddedEvent(EntryAddedEvent<CircuitBreaker> entryAddedEvent) {
                System.out.println("onEntryAddedEvent");
            }

            @Override
            public void onEntryRemovedEvent(EntryRemovedEvent<CircuitBreaker> entryRemoveEvent) {
                System.out.println("onEntryRemovedEvent");
            }

            @Override
            public void onEntryReplacedEvent(EntryReplacedEvent<CircuitBreaker> entryReplacedEvent) {
                System.out.println("onEntryReplacedEvent");
            }
        }).circuitBreaker("My_circuit");
    }

}
