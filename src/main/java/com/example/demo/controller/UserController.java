package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.model.RestResult;
import com.example.demo.model.UserInformation;
import com.example.demo.param.ChangeForgotPasswordParam;
import com.example.demo.param.ChangePasswordParam;
import com.example.demo.param.UserRegisterParam;
import com.example.demo.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Objects;

/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2021-03-08 23:39:25
 */
@RestController
@RequestMapping("/user")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/selectOne")
    public User selectOne(Integer id) {
        return this.userService.queryById(id);
    }


    /**
     * 注册接口，注册时会向注册邮箱发送验证邮件，未通过的用户会处于未验证状态，无法登陆
     *
     * @param userRegisterParam 注册的表单
     * @param bindingResult     表单验证参数
     * @return 是否成功
     */
    @PostMapping("/register")
    public RestResult<Boolean> register(@Validated @RequestBody UserRegisterParam userRegisterParam,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return RestResult.error(-1, Objects.requireNonNull(bindingResult.getFieldError()).getField() + "," +
                    bindingResult.getFieldError().getDefaultMessage());
        }
        return RestResult.success(this.userService.register(userRegisterParam));
    }

    /**
     * 忘记密码接口
     *
     * @param email 验证的邮箱
     * @return 返回是否成功
     */
    @PostMapping("/forgotPassword")
    public RestResult<Boolean> forgotPassword(@RequestParam("email") String email) {
        return RestResult.success(this.userService.forgotPassword(email));
    }

    /**
     * 验证修改密码
     *
     * @param passwordParam 验证修改密码表单
     * @param bindingResult 表单验证
     * @return 是否成功
     */
    @PostMapping("/changeForgotPassword")
    public RestResult<Boolean> changeForgotPassword(@Validated @RequestBody ChangeForgotPasswordParam passwordParam,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return RestResult.error(-1, Objects.requireNonNull(bindingResult.getFieldError()).getField() + "," +
                    bindingResult.getFieldError().getDefaultMessage());
        }
        return RestResult.success(this.userService.changeForgotPassword(passwordParam));
    }

    /**
     * 解除未验证的状态的接口
     *
     * @param param         注册表单
     * @param bindingResult 表单验证
     * @return 是否成功
     */
    @PostMapping("/unlock")
    public RestResult<Boolean> unlock(@Validated @RequestBody UserRegisterParam param,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return RestResult.error(-1, Objects.requireNonNull(bindingResult.getFieldError()).getField() + "," +
                    bindingResult.getFieldError().getDefaultMessage());
        }
        return RestResult.success(this.userService.unlock(param));
    }

    @PostMapping("/changePassword")
    public RestResult<Boolean> changPassword(@Validated @RequestBody ChangePasswordParam param,
                                             BindingResult bindingResult,
                                             Authentication authentication){
        if (bindingResult.hasErrors()) {
            return RestResult.error(-1, Objects.requireNonNull(bindingResult.getFieldError()).getField() + "," +
                    bindingResult.getFieldError().getDefaultMessage());
        }
        UserInformation userInformation = (UserInformation)authentication.getPrincipal();
        return RestResult.success(this.userService.changePassword(param,userInformation));
    }
}
