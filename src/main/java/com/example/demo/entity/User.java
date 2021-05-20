package com.example.demo.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by 林夕
 * Date 2021/3/8 21:44
 */
@Data
public class User {

    private Integer id;

    private String username;

    private String password;

    private String openId;

    private String avatar;

    private String sex;

    private String graduatedSchool;

    private String phone;

    private String role;

    private String email;

    private Integer state;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private LocalDateTime deleteTime;
}
