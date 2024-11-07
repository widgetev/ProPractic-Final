package com.example.propracticfinal.controller;

import com.example.propracticfinal.dto.LimitDTO;
import com.example.propracticfinal.dto.LimitResponse;
import com.example.propracticfinal.service.LimitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/payments")
public class LimitController {
    private final LimitService limitService;

    public LimitController(LimitService limitService) {
        this.limitService = limitService;
    }

    @PostMapping("/checklimit")
    public LimitResponse checLimit(@RequestBody LimitDTO limit) {
        log.info("Получен запрос проверки лимита");
        return new LimitResponse(limitService.checLimit(limit));
    }

    @PostMapping("/freeze")
    public LimitResponse doFreeze(@RequestBody LimitDTO limit) {
        return new LimitResponse(limitService.doFreeze(limit));
    }

    @PostMapping("/rollback")
    public LimitResponse doRollback(@RequestBody LimitDTO limit) {
        return new LimitResponse(limitService.doRollback(limit));
    }

    @PostMapping("/confirm")
    public LimitResponse doConfirm(@RequestBody LimitDTO limit) {
        return new LimitResponse(limitService.doConfirm(limit));
    }


}
