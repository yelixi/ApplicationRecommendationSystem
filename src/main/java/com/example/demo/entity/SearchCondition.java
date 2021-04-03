package com.example.demo.entity;

import lombok.Data;

@Data
public class SearchCondition {
    private Integer id;

    private String schoolType;// 学校属性，985、211

    private String schoolName;//学校名称

    private String lowestScore;//录取的最低分

    private String position;//学校位置

    private String region;//学校地区

    public String schoolCode;//学校代码

    public String plan;//招生计划

    public String accessMajor;//可报专业

    public String detial;//学校信息

    public String recommendReason;//推荐理由

    public String analyse;//结果分析
}
