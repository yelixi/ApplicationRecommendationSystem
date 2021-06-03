package com.example.demo.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by 林夕
 * Date 2021/3/12 16:25
 */
@Data
public class ChangeForgotPasswordParam {

    @NotNull(message = "密码不能为空")
    private String password;

    @NotNull(message = "验证码不能为空")
    private String code;

    @NotNull(message = "email不能为空")
    private String email;
}
