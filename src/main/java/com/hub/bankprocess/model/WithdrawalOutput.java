package com.hub.bankprocess.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WithdrawalOutput {
    private String action;
    private String code;
    private String autorizationCode;

    public WithdrawalOutput() { }

    public WithdrawalOutput(String action, String code) {
        this.action = action;
        this.code = code;
    }

    public WithdrawalOutput(String action, String code, String autorizationCode) {
        this.action = action;
        this.code = code;
        this.autorizationCode = autorizationCode;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty(value = "autorization_code")
    public String getAutorizationCode() {
        return autorizationCode;
    }

    public void setAutorizationCode(String autorizationCode) {
        this.autorizationCode = autorizationCode;
    }
}
