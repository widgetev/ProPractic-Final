package com.example.propracticfinal.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;


import java.math.BigDecimal;

@Getter
@ConfigurationProperties(prefix = "service.limits.default")
public class PaymentConfig {

    private final BigDecimal amount;
    private final String cleanPeriod;

    public PaymentConfig(BigDecimal amount, String cleanPeriod) {
        this.amount = amount;
        this.cleanPeriod = cleanPeriod;
    }


}
