package com.example.demo.Vo;

import com.example.demo.entity.Colleges;
import com.example.demo.entity.Papers;
import com.example.demo.entity.Professions;
import lombok.Data;

import java.util.List;

/**
 * Created by 林夕
 * Date 2021/5/16 19:01
 */
@Data
public class UserFocusVo {
    String openId;

    List<Colleges> colleges;

    List<Papers> papers;

    List<Professions> professions;
}
