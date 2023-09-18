package com.hub.bankprocess.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class AccountDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final String UPDATE_AMOUNT_QUERY = "UPDATE account SET amount = :amount WHERE card_number = :cardNumber";
    private final String GET_AMOUNT_QUERY = "SELECT amount FROM account WHERE card_number = :cardNumber";
    private final String CARD_NUMBER_EXISTS_QUERY = "SELECT count(*) FROM account WHERE card_number = :cardNumber";

    public AccountDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void updateAmount(BigInteger cardNumber, Double newAmount) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("amount", newAmount)
                .addValue("cardNumber", cardNumber);
        jdbcTemplate.update(UPDATE_AMOUNT_QUERY, params);
    }

    public Double getAmount(BigInteger cardNumber) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("cardNumber", cardNumber);
        return jdbcTemplate.queryForObject( GET_AMOUNT_QUERY, params, Double.class);
    }

    public boolean cardNumberExists(BigInteger cardNumber) {
        SqlParameterSource param = new MapSqlParameterSource("cardNumber", cardNumber);
        int count = jdbcTemplate.queryForObject(CARD_NUMBER_EXISTS_QUERY, param, Integer.class);
        return count > 0;
    }
}
