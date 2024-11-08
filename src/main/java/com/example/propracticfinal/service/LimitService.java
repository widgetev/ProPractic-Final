package com.example.propracticfinal.service;

import com.example.propracticfinal.config.PaymentConfig;
import com.example.propracticfinal.db.entity.LimitEntity;
import com.example.propracticfinal.db.repository.LimitRepository;
import com.example.propracticfinal.dto.LimitDTO;
import com.example.propracticfinal.exception.RequestException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class LimitService {

    private final PaymentConfig config;
    private final LimitRepository limitRepository;

    public LimitService(PaymentConfig config, LimitRepository limitRepository) {
        this.config = config;
        this.limitRepository = limitRepository;
    }

    public boolean checLimit(LimitDTO limitDTO) {
        log.info("Поищу лимит на сумму {} для user= {}", limitDTO.amount(), limitDTO.userid() );
        LimitEntity limit = limitRepository.findByUserId(limitDTO.userid());
        if(limit == null) {
            log.info("Лимит для user= {} не найден, заведу новый ", limitDTO.userid() );
            limit = new LimitEntity();
            limit.setAmount(config.getAmount());
            limit.setLockAmount(BigDecimal.ZERO);
            limit.setUserId(limitDTO.userid());
            limitRepository.save(limit);
            log.info(" Создан новый лимит : id= {}, user ={}, amount= {}, loockAmout = {} ", limit.getId(), limit.getUserId(), limit.getAmount(), limit.getLockAmount());
        }
        boolean tmp = isEnough(limitDTO, limit);
        log.info("Пороверка лимита показала : {}", tmp);
        return tmp;
    }

    public boolean doFreeze(LimitDTO limitDTO) {
        //за время пути что-то могло измениться. А может вообещ не проверяли
        if(checLimit(limitDTO)) {
            LimitEntity limit = limitRepository.findByUserId(limitDTO.userid());
            limit.setLockAmount(limit.getLockAmount().add(limitDTO.amount()));
            limitRepository.save(limit);
            return true;
        }
      return false;
    }

    @Transactional
    public boolean doConfirm(LimitDTO limitDTO) {
        LimitEntity limit = getLimitEntity(limitDTO);
        try {
            if(limit.getLockAmount().compareTo(limitDTO.amount()) >=0 ) {
                limit.setLockAmount(limit.getLockAmount().subtract(limitDTO.amount()));
                limit.setAmount(limit.getAmount().subtract(limitDTO.amount()));
                limitRepository.save(limit);
            }
            else {
                throw new RequestException("Ошибка при проверке блокированного лимита");
            }
        }catch (RuntimeException e) {
            throw new RequestException("Ошибка при изменении лимита");
        }
        return true;
    }


    @Transactional
    public boolean doRollback(LimitDTO limitDTO) {
        LimitEntity limit = getLimitEntity(limitDTO);
        if(limit.getLockAmount().compareTo(limitDTO.amount()) >=0 ) {
            limit.setLockAmount(limit.getLockAmount().subtract(limitDTO.amount()));
            limitRepository.save(limit);
        }
        else {
            throw new RequestException("Ошибка отмене блокировки на " + limitDTO.amount() + " рублей.");
        }
        return true;
    }

    @Transactional
    public boolean doCashback(LimitDTO limitDTO) {
        try {
            LimitEntity limit = getLimitEntity(limitDTO);
            limit.setAmount(limit.getAmount().add(limitDTO.amount()));
            limitRepository.save(limit);
            return true;
        }catch (RuntimeException e) {
            throw new RequestException("Ошибка при возврате средств");
        }
    }

    private LimitEntity getLimitEntity(LimitDTO limitDTO) {
        LimitEntity limit = limitRepository.findByUserId(limitDTO.userid());
        if (limit == null ) {
            throw new RequestException("Лимит не найден");
        }
        return limit;
    }

    private static boolean isEnough(LimitDTO limitDTO, LimitEntity limit) {
        return limit.getAmount().subtract(limit.getLockAmount()).compareTo(limitDTO.amount()) >= 0;
    }
}
