package com.example.demo.model;

import lombok.Data;

/**
 * Created by 林夕
 * Date 2021/4/3 15:14
 */
@Data
public class MajorConditions {

    /**
     * 专业名称
     */
    private String majorName;

    /**
     * 专业类型(如：计算机类)
     */

    private String majorType;

    /**
     * 专业层次(一本、二本)
     */
    private String majorArrangement;
}
