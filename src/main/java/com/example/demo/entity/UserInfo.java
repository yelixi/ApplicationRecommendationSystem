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

    private String name;

    private String avatar;

    private String location;

    private String mode;

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

    private Integer technology;

    private Integer comprehensiveScience;

    private Integer comprehensiveLiberalArts;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private LocalDateTime deleteTime;
}
