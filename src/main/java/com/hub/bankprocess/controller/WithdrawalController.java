package com.hub.bankprocess.controller;

import com.hub.bankprocess.model.WithdrawalInput;
import com.hub.bankprocess.model.WithdrawalOutput;
import com.hub.bankprocess.service.WithdrawalService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WithdrawalController {

    private final WithdrawalService service;

    public WithdrawalController(WithdrawalService service) {
        this.service = service;
    }

    @MessageMapping("/transaction")
    @SendTo("/result")
    public WithdrawalOutput execute(final WithdrawalInput input) throws Exception {
        return service.execute(input);
    }

}
