package com.iqianjin.appperformance.db.model;

import java.util.Date;

public class ElementLocation{

    private Long id;

    private String name;

    private String value;

    private Integer platform;

    private Date create_time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getValue() {
        return value;
    }


    public void setValue(String value) {
        this.value = value;
    }


    public Integer getPlatform() {
        return platform;
    }


    public void setPlatform(Integer platform) {
        this.platform = platform;
    }


    public Date getCreate_time() {
        return create_time;
    }


    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

}
