package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.model.RestResult;
import com.example.demo.param.UserRegisterParam;
import com.example.demo.service.UserService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    @PostMapping("/register")
    public RestResult<Boolean> register(@Validated @RequestBody UserRegisterParam userRegisterParam,
                                        BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return RestResult.error(-1, Objects.requireNonNull(bindingResult.getFieldError()).getField()+","+
                    bindingResult.getFieldError().getDefaultMessage());
        }
        return RestResult.success(this.userService.register(userRegisterParam));
    }
}
