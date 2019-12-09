package com.iqianjin.appperformance.common.constant;

/**
 * @author Ricky Fung
 */
public enum ReceiverType {
    MAIL(1, "邮箱地址"),
    PHONE(2, "手机号"),
    ;
    private int value;
    private String desc;
    ReceiverType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

}
