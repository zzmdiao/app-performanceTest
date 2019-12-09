package com.iqianjin.appperformance.db.model;

import java.util.List;


public class RequestDataVO {
    /**
     * 待发送成员的手机号列表
     */
    private List<String> phoneNumberList;

    /**
     * 待发送成员邮箱列表
     */
    private List<String> emailList;
    /**
     * 部门Id列表
     */
    private List<String> deptIdList;

    /**
     * 最长2048个字节
     */
    private String content;

    public List<String> getPhoneNumberList() {
        return phoneNumberList;
    }

    public void setPhoneNumberList(List<String> phoneNumberList) {
        this.phoneNumberList = phoneNumberList;
    }

    public List<String> getEmailList() {
        return emailList;
    }

    public void setEmailList(List<String> emailList) {
        this.emailList = emailList;
    }

    public List<String> getDeptIdList() {
        return deptIdList;
    }

    public void setDeptIdList(List<String> deptIdList) {
        this.deptIdList = deptIdList;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
