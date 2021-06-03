package com.example.demo.entity;

import lombok.Data;
/*
* 排序问题
*
* */
@Data
public class Ranking {
    private String schoolRank;//按照学校排名

    private String majorRank;//按照专业排名

    private String aroundArea;//按照附近地区排名
}
