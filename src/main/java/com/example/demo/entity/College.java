package com.example.demo.entity;

import lombok.Data;

/**
 * Created by 林夕
 * Date 2021/5/20 0:33
 */
@Data
public class College {
    private Integer id;

    private String name;

    //主办部门
    private String competentDepartment;

    private String schoolCord;

    private String province;

    private Integer minimumScore;

    private Integer minimumRank;

    private String uneven;
}
