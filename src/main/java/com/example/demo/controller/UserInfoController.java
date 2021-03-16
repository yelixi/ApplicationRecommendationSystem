package com.example.demo.controller;

import com.example.demo.entity.UserInfo;
import com.example.demo.model.RestResult;
import com.example.demo.model.UserInformation;
import com.example.demo.service.UserInfoService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

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


    /**
     * 修改用户信息
     * @param userupdateInfo
     * @param authentication
     * @return
     */
    @PostMapping("/update")
    public RestResult<UserInfo> update(@RequestBody UserInfo userupdateInfo, Authentication authentication)
    {
        UserInformation userInformation=(UserInformation)authentication.getPrincipal();
        UserInfo index=new UserInfo();
        index.setUserId(userInformation.getId());
        List<UserInfo> userInfos=this.userInfoService.queryAll(index);
        if (userInfos==null)
        {
            userupdateInfo.setUserId(userInformation.getId());
            return RestResult.success(this.userInfoService.insert(userupdateInfo));
        }else{
            userupdateInfo.setId(userInfos.get(0).getId());
            return RestResult.success(this.userInfoService.update(userupdateInfo));
        }
    }

}