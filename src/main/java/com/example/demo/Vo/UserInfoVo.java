package com.example.demo.Vo;

import com.example.demo.entity.User;
import com.example.demo.entity.UserInfo;
import com.example.demo.model.UserInformation;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by 林夕
 * Date 2021/3/17 15:20
 */
@Getter
@Setter
@ToString
public class UserInfoVo extends UserInformation {

    public UserInfoVo(User user) {
        super(user);
    }

    private UserInfo userInfo;
}
