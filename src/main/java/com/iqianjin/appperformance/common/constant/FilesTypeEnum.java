package com.iqianjin.appperformance.common.constant;

/**
 * 0:购买爱盈宝、月进宝 1:切换tab 2:浏览资金流水 3:浏览我的资产 4：浏览出借记录
 */
public enum FilesTypeEnum {
    INVEST_PRODUCT("0", "购买爱盈宝、月进宝"),
    SWITCH_TAB("1", "切换tab"),
    BROW_FUNDFLOW("2", "浏览资金流水"),
    BROW_MY_ASSERTS("3", "浏览我的资产"),
    BROW_LENDRECORD("4", "浏览出借记录"),
    ALL("0,1,2,3,4", "所有场景"),
    ;
    private String value;
    private String desc;
    FilesTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String[] getValue() {
        return new String[]{value};
    }

    public String getDesc() {
        return desc;
    }

}
