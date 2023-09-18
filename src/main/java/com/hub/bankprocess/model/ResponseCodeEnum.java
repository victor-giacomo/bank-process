package com.hub.bankprocess.model;

public enum ResponseCodeEnum {
    SUCCESS("00"),
    INVALID_ACCOUNT("14"),
    INSUFFICIENT_FUNDS("51"),
    ERROR("96");

    String code;

    ResponseCodeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
