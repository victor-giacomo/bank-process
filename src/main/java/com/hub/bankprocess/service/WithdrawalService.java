package com.hub.bankprocess.service;

import com.hub.bankprocess.dao.AccountDao;
import com.hub.bankprocess.dao.TransactionDao;
import com.hub.bankprocess.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Service
public class WithdrawalService {

    private final AccountDao accountDao;
    private final TransactionDao transactionDao;

    public WithdrawalService(AccountDao dao, TransactionDao transactionDao) {
        this.accountDao = dao;
        this.transactionDao = transactionDao;
    }

    @Transactional
    public WithdrawalOutput execute(WithdrawalInput transaction) {

        try {
            BigInteger cardNumber = transaction.getCardnumber();
            Double withdrawAmount = transaction.getAmount();

            if(withdrawAmount <= 0) {
                return error();
            }

            if(!accountDao.cardNumberExists(transaction.getCardnumber())) {
                return invalidAccount();
            }

            Double amountAccount = accountDao.getAmount(transaction.getCardnumber());

            if(withdrawAmount > amountAccount) {
                return insufficientFunds();
            }

            accountDao.updateAmount(cardNumber, amountAccount - withdrawAmount);
            Number idTransaction = transactionDao.saveTransaction(cardNumber, withdrawAmount);

            return success(idTransaction);

        } catch (Exception e) {
            e.printStackTrace();
            return error();
        }
    }

    @Transactional(readOnly = true)
    public CardInfo getCardInfo(BigInteger cardNumber) {
        List<Transaction> transactions = transactionDao.getTransactions(cardNumber);
        Double availableAmount = accountDao.getAmount(cardNumber);
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardNumber(cardNumber);
        cardInfo.setAvailableAmount(availableAmount);
        cardInfo.setTransactions(transactions);
        return cardInfo;
    }

    private WithdrawalOutput invalidAccount() {
        return getTransactionOutput(ResponseCodeEnum.INVALID_ACCOUNT);
    }

    private WithdrawalOutput success(Number idTransaction) {
        WithdrawalOutput output = getTransactionOutput(ResponseCodeEnum.SUCCESS);
        output.setAutorizationCode(String.format( "%06d", idTransaction ));
        return output;
    }

    private WithdrawalOutput error() {
        return getTransactionOutput(ResponseCodeEnum.ERROR);
    }

    private WithdrawalOutput insufficientFunds() {
        return getTransactionOutput(ResponseCodeEnum.INSUFFICIENT_FUNDS);
    }

    private WithdrawalOutput getTransactionOutput(ResponseCodeEnum responseCodeEnum) {
        return new WithdrawalOutput(ActionEnum.WITHDRAW.getCode(), responseCodeEnum.getCode());
    }

}
