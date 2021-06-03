package com.example.demo.controller;

import com.example.demo.Vo.UserFocusVo;
import com.example.demo.Vo.UserInfoVo;
import com.example.demo.entity.User;
import com.example.demo.entity.UserInfo;
import com.example.demo.enums.ResultEnum;
import com.example.demo.model.RestResult;
import com.example.demo.model.UserInformation;
import com.example.demo.service.ImageService;
import com.example.demo.service.UserInfoService;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

/**
 * (UserInfo)表控制层
 *
 * @author whz
 * @since 2021-03-08 23:42:46
 */
@RestController
@Slf4j
@RequestMapping("/userInfo")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;
    @Resource
    private ImageService imageService;

    @Resource
    private UserService userService;

    /**
     * 允许的图片路径
     */
    private static final String[] SUFFIX_WHITE_LIST = {"jpg", "png", "gif", "bmp"};
    private static final String[] DIR_WHITE_LIST = {"avatar", "banner"};
    private static final HashSet<String> SUFFIX_LIST;
    private static final HashSet<String> DIR_LIST;

    static {
        SUFFIX_LIST = new HashSet<>(4);
        DIR_LIST = new HashSet<>(2);
        SUFFIX_LIST.addAll(Arrays.asList(SUFFIX_WHITE_LIST));
        DIR_LIST.addAll(Arrays.asList(DIR_WHITE_LIST));
    }


    /**
     * 修改用户信息
     *
     * @param userUpdateInfo 用户信息表单
     * @return 是否成功
     */
    @PutMapping("/updateUserInfo")
    public RestResult<UserInfo> update(@RequestBody UserInfoVo userUpdateInfo) {
        //获取openId
        User user = userService.queryByOpenId(userUpdateInfo.getOpenId());
        //OpenId open = openIdService.selectByOpenId(userUpdateInfo.getOpenId());
        //获取传入的userInfo
        UserInfo userInfo = userUpdateInfo.getUserInfo();
        //获取表中是否已有用户信息
        UserInfo userInfos = userInfoService.queryByUserId(user.getId());
        //设置唯一索引UserId
        userInfo.setUserId(user.getId());
        if (userInfos == null) {
            //如果表中没有数据则插入
            //userUpdateInfo.setUserInfo(userInfo);
            return RestResult.success(this.userInfoService.insert(userInfo));
        } else {
            //否则更新
            return RestResult.success(this.userInfoService.update(userInfo));
        }
    }

    @PutMapping("/updateUser")
    public RestResult<User> updateUser(@RequestBody User user){
        return RestResult.success(userService.updateByOpenId(user));
    }

    /**
     * 头像上传
     *
     * @param file 图片文件
     * @param type 类型 如：avatar
     * @return com.example.demo.model.RestResult
     */
    @PostMapping("uploadAvatar")
    public RestResult<String> uploadAvatar(@RequestParam("file") MultipartFile file, String type, String openId) {
        if (file == null) {
            return RestResult.error("file不存在");
        }
        if (!DIR_LIST.contains(type)) {
            return RestResult.error("非法路径");
        }

        String fileName = file.getOriginalFilename();
        Objects.requireNonNull(fileName);
        String suffix = fileName.substring(fileName.lastIndexOf(".")).replace(".", "");
        if (!SUFFIX_LIST.contains(suffix.toLowerCase())) {
            return RestResult.error(ResultEnum.UNSUPPORTED_MEDIA_TYPE);
        }
        String url = imageService.saveFileToLocalServeWithDir(file, type);
        if (url == null) {
            return RestResult.error("图片 " + file.getOriginalFilename() + " 上传失败");
        } else {
            User user = userService.queryByOpenId(openId);
            if (user == null) {
                return RestResult.error(ResultEnum.NO_PERMISSION_ACCESS);
            }
            user.setAvatar(url);
            userService.update(user);
            return RestResult.success(url);
        }
    }

    /**
     * 获取登陆用户信息
     * @param openId 用户唯一索引openId
     * @return com.example.demo.model.RestResult
     */
    @GetMapping("/doLoginGet")
    public RestResult<UserInfoVo> loginGet(@RequestParam("openId") String openId){
        User user = userService.queryByOpenId(openId);
        UserInfo userInfo = userInfoService.queryByUserId(user.getId());
        UserInfoVo userInfoVo = new UserInfoVo(new UserInformation(user));
        userInfoVo.setPassword("******");
        userInfoVo.setUserInfo(userInfo);
        userInfoVo.setOpenId(openId);
        if (userInfo==null){
            return RestResult.error("登陆信息不存在");
        }else {
            return RestResult.success(userInfoVo);
        }
    }

    /**
     * 查找特定用户信息
     * @param userId 用户id
     * @return com.example.demo.model.RestResult
     */
    @GetMapping("/findInfo")
    public RestResult<UserInfoVo> findById(@RequestParam("userId") Integer userId){
        User user = userService.queryById(userId);
        UserInfo userInfo = userInfoService.queryByUserId(userId);
        UserInfoVo userInfoVo = new UserInfoVo(new UserInformation(user));
        userInfoVo.setPassword("******");
        userInfoVo.setUserInfo(userInfo);
        if (userInfo ==null){
            return RestResult.error("用户信息不存在");
        }else {
            return RestResult.success(userInfoVo);
        }
    }

    @GetMapping("/getFocusInfo")
    RestResult<UserFocusVo> getFocusInfo(@RequestParam("openId") String openId){
        return RestResult.success(userService.getFocusInfo(openId));
    }
}