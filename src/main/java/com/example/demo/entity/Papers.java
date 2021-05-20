package com.example.demo.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by 林夕
 * Date 2021/5/20 0:07
 */
@Data
public class Papers {
    private Integer id;

    private String name;

    private String openId;

    private String detail;

    private Date publishTime;

    private Integer readTimes;

    private Integer thumbUpTimes;

    private String source;
}
