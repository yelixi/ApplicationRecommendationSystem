package com.example.demo.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by 林夕
 * Date 2021/3/8 21:44
 */
@Data
public class UserInfo {
    private Integer id;

    private Integer userId;

    /**
     * 用户的真实姓名
     */
    private String name;

    private String location;

    /**
     * 高考模式
     */
    private String mode;

    /**
     * 期望分数
     */
    private Integer expectScore;

    private Integer chinese;

    private Integer math;

    private Integer english;

    private Integer physics;

    private Integer chemistry;

    private Integer biology;

    private Integer politics;

    private Integer history;

    private Integer geography;

    private Integer rank;

    private Integer totalScore;

    private Integer year;

    /**
     * 技术
     */
    private Integer technology;

    /**
     * 理综
     */
    private Integer comprehensiveScience;

    /**
     * 文综
     */
    private Integer comprehensiveLiberalArts;

    private String subject;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private LocalDateTime deleteTime;
}
