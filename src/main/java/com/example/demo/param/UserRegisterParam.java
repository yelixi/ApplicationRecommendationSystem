package com.example.demo.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by 林夕
 * Date 2021/3/9 13:28
 */
@Data
public class UserRegisterParam {

    @NotNull(message = "username不能为空")
    private String username;

    @NotNull(message = "password不能为空")
    private String password;

    @NotNull(message = "phone不能为空")
    private String phone;

    @NotNull(message = "email不能为空")
    private String email;
}
