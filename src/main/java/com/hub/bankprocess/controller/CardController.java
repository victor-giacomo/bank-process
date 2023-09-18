package com.hub.bankprocess.controller;

import com.hub.bankprocess.model.CardInfo;
import com.hub.bankprocess.service.WithdrawalService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
public class CardController {

    private final WithdrawalService service;

    public CardController(WithdrawalService service) {
        this.service = service;
    }

    @RequestMapping(value = "/card/{cardNumber}/info")
    public CardInfo info(@PathVariable final BigInteger cardNumber) throws Exception {
        return service.getCardInfo(cardNumber);
    }

}
