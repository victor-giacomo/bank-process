package com.hub.bankprocess.dao;

import com.hub.bankprocess.model.Transaction;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class TransactionDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final String SAVE_TRANSACTION_QUERY = "INSERT INTO transaction(account_id, date, amount) values (:account_id, :date, :amount)";
    private final String GET_TRANSACTIONS_QUERY = "SELECT id, account_id AS cardNumber, date, amount FROM transaction " +
            "WHERE account_id = :account_id ORDER BY date DESC LIMIT 10";

    public TransactionDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Number saveTransaction(BigInteger cardNumber, Double amount) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("account_id", cardNumber)
                .addValue("date", LocalDateTime.now())
                .addValue("amount", amount);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(SAVE_TRANSACTION_QUERY, params, keyHolder);
        return keyHolder.getKey();
    }

    public List<Transaction> getTransactions(BigInteger cardNumber) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("account_id", cardNumber);
        return jdbcTemplate.query( GET_TRANSACTIONS_QUERY, params,
                new BeanPropertyRowMapper<>(Transaction.class));
    }
}
