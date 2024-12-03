package com.epam.vital.gym_crm.actuator;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class CountApiCallsGauge {
    private final MeterRegistry meterRegistry;
    private int count;

    public CountApiCallsGauge(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        meterRegistry.gauge("api.calls.count", this, CountApiCallsGauge::getCount);
    }

    public void apiCalled() {
        count++;
    }

    public int getCount() {
        return count;
    }
}
