package com.iqianjin.appperformance.exception;

public enum ApplicationCode {
    INTERNAL_SERVER_ERROR(500, "Internal Server Error", "服务器内部错误"),
    INVALID_PARAM(400, "invalid param", "参数错误"),
    USER_LIMIT_ERROR(20005, "点击过于频繁，请稍候重试", "点击过于频繁，请稍候重试"),
    USER_CLICK_LIMIT_ERROR(20005, "正在处理，请稍候", "正在处理，请稍候"),
    ;

    private int code;
    private String message;
    private String desc;
    ApplicationCode(int code, String message, String desc) {
        this.code = code;
        this.message = message;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
