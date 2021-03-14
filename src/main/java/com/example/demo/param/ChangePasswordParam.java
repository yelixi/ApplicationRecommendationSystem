package com.example.demo.param;

import lombok.Data;

import javax.validation.constraints.NotNull;
/**
 * Created by yyh
 * Date 2021/3/13 22:08
 */

@Data
public class ChangePasswordParam {

    @NotNull(message = "旧密码不能为空，请输入旧密码")
    private String oldPassword;

    @NotNull(message = "新密码不能为空")
    private String newPassword;

    @NotNull(message = "请再次确认新密码")
    private String comfirmPassword;

}
