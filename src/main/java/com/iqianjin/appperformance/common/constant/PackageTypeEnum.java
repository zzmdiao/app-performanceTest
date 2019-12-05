package com.iqianjin.appperformance.common.constant;

public enum PackageTypeEnum {
    //1:debug包（可切换host）, 2:V3包（不可切换host）, 3:生产包（不可切换host）
    TEST_PACKAGE("1", "可切换host"),
    V3_PACKAGE("2", "V3包（不可切换host）"),
    V2_PACKAGE("3", "生产包（不可切换host）"),;

    private String type;
    private String desc;

    PackageTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
