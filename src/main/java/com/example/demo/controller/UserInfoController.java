package com.example.demo.controller;

import com.example.demo.entity.UserInfo;
import com.example.demo.enums.ResultEnum;
import com.example.demo.model.RestResult;
import com.example.demo.model.UserInformation;
<<<<<<< HEAD
import com.example.demo.service.UserInfoService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;
=======
import com.example.demo.service.ImageService;
import com.example.demo.service.UserInfoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
>>>>>>> bac87e27bb1d77eab3f10d5834fbb84bde80c479

/**
 * (UserInfo)表控制层
 *
 * @author whz
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
    @Resource
    private ImageService imageService;
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

    @PostMapping("uploadAvatar")
    public RestResult uploadAvatar(MultipartHttpServletRequest request, String type, Principal principal) {
        MultipartFile file = request.getFile("file");
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
            UserInformation userInformation = (UserInformation) principal;
            if (userInformation == null) {
                return RestResult.error(ResultEnum.NO_PERMISSION_ACCESS);
            }
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(userInformation.getId());
            userInfo.setAvatar(url);
            userInfoService.update(userInfo);
            return RestResult.success(url);
        }
    }
}