package com.example.demo.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Feedback {
    private Integer id;

    private Integer openId;

    private String detail;

    private String name;

    private String telNumber;

    private Integer userId;

    private Date updateTime;

}
