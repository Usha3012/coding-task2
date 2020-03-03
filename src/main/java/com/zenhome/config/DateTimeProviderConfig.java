package com.zenhome.config;

import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

@Component("dateTimeProvider")
public class DateTimeProviderConfig implements DateTimeProvider {

    @Override
    @NotNull
    public Optional<TemporalAccessor> getNow() {
        return Optional.of(OffsetDateTime.now());
    }

}
