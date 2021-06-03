package com.example.demo.entity;

import java.util.Date;

public class Consult {
    private Integer id;

    private String name;

    private String detail;

    private Date publishTime;

    private Integer readTimes;

    private Integer thumbUpTimes;

    private String source;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getReadTimes() {
        return readTimes;
    }

    public void setReadTimes(Integer readTimes) {
        this.readTimes = readTimes;
    }

    public Integer getThumbUpTimes() {
        return thumbUpTimes;
    }

    public void setThumbUpTimes(Integer thumbUpTimes) {
        this.thumbUpTimes = thumbUpTimes;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}