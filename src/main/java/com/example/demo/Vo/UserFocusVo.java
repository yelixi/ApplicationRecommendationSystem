package com.example.demo.Vo;

import lombok.Data;

import java.util.List;

/**
 * Created by 林夕
 * Date 2021/5/16 19:01
 */
@Data
public class UserFocusVo {
    String openId;

    List<String> colleges;

    List<String> papers;

    List<String> professions;
}
