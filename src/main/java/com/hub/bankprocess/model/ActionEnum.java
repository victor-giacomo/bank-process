package com.hub.bankprocess.model;

public enum ActionEnum {
    WITHDRAW("withdraw");

    private String code;

    ActionEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
