package com.iqianjin.appperformance.common.constant;

public enum Env {
    DEV(1, "开发环境-办公网络", "http://smsmg.iqianjin.com/qywx/api/v1/sendText"),
    TEST(2, "测试环境-物理机", "http://smsmg.iqianjin.com/qywx/api/v1/sendText"),
    OKD(3, "测试环境-OKD", "http://qywx.okd.iqianjin.test/qywx/api/v1/sendText"),
    ;
    private int type;
    private String desc;
    private String serviceUrl;
    Env(int type, String desc, String serviceUrl) {
        this.type = type;
        this.desc = desc;
        this.serviceUrl = serviceUrl;
    }

    public static Env of(String value) {
        for (Env env : Env.values()) {
            if (env.name().equalsIgnoreCase(value)) {
                return env;
            }
        }
        return null;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }
}
