package com.onlyforchris.store.core.notify;

/**
 * @author: Chris
 * @create: 2024-03-24 02:09
 **/
public enum NotifyType {
    PAY_SUCCEED("paySucceed"),
    SHIP("ship"),
    REFUND("refund"),
    CAPTCHA("captcha");

    private final String type;

    NotifyType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}

