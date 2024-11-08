package com.example.propracticfinal.job;

import com.example.propracticfinal.config.PaymentConfig;
import com.example.propracticfinal.db.entity.LimitEntity;
import com.example.propracticfinal.db.repository.LimitRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Component
@EnableScheduling
@EnableAsync
public class ResetLimitJob {

    LimitRepository limitRepository;
    PaymentConfig config;

    public ResetLimitJob(LimitRepository limitRepository, PaymentConfig config) {
        this.limitRepository = limitRepository;
        this.config = config;
    }

    @Async
    @Scheduled(fixedRateString = "${service.limits.default.clean-period}")
    public void resetLimit() {
        List<LimitEntity> listEntity = limitRepository.findAllByForReset(config.getAmount());
        for (LimitEntity entity : listEntity) {
            entity.setAmount(config.getAmount());
            entity.setLockAmount(BigDecimal.ZERO);
        }

        for (LimitEntity entity : listEntity) {
            log.info("limit {} = {} : {}", entity.getId(), entity.getAmount(), entity.getLockAmount());
        }
        limitRepository.saveAll(listEntity);
    }
}
