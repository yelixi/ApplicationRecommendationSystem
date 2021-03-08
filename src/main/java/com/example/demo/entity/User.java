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

    private String phone;

    private String role;

    private LocalDateTime create_time;

    private LocalDateTime update_time;

    private LocalDateTime delete_time;
}
