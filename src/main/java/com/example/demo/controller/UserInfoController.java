package com.example.demo.controller;

import com.example.demo.entity.UserInfo;
import com.example.demo.model.RestResult;
import com.example.demo.service.UserInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (UserInfo)表控制层
 *
 * @author makejava
 * @since 2021-03-08 23:42:46
 */
@RestController
@RequestMapping("userInfo")
public class UserInfoController {
    /**
     * 服务对象
     */
    @Resource
    private UserInfoService userInfoService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public UserInfo selectOne(Integer id) {
        return this.userInfoService.queryById(id);
    }

    @PostMapping("/update")
    public RestResult<UserInfo> update(@RequestBody UserInfo userupdateInfo)
    {
        return RestResult.success(this.userInfoService.update(userupdateInfo));
    }

}