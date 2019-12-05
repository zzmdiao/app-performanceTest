package com.iqianjin.appperformance.db.model;

import java.util.Date;

public class Device {

    public static final Integer OFFLINE_STATUS = 0;
    public static final Integer USING_STATUS = 1;
    public static final Integer IDLE_STATUS = 2;

    private String id;

    private String name;

    private String systemVersion;

    private String cpuInfo;

    private String memSize;

    private Integer screenWidth;

    private Integer screenHeight;

    private Integer platform;

    private Integer status;

    private Date lastOnlineTime;

    private Date lastOfflineTime;

    private String username;

    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion == null ? null : systemVersion.trim();
    }

    public String getCpuInfo() {
        return cpuInfo;
    }

    public void setCpuInfo(String cpuInfo) {
        this.cpuInfo = cpuInfo == null ? null : cpuInfo.trim();
    }

    public String getMemSize() {
        return memSize;
    }

    public void setMemSize(String memSize) {
        this.memSize = memSize == null ? null : memSize.trim();
    }

    public Integer getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(Integer screenWidth) {
        this.screenWidth = screenWidth;
    }

    public Integer getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(Integer screenHeight) {
        this.screenHeight = screenHeight;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getLastOnlineTime() {
        return lastOnlineTime;
    }

    public void setLastOnlineTime(Date lastOnlineTime) {
        this.lastOnlineTime = lastOnlineTime;
    }

    public Date getLastOfflineTime() {
        return lastOfflineTime;
    }

    public void setLastOfflineTime(Date lastOfflineTime) {
        this.lastOfflineTime = lastOfflineTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
