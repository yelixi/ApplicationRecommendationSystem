package com.example.demo.model;

import lombok.Data;

/**
 * Created by 林夕
 * Date 2021/4/1 16:59
 */
@Data
public class MajorInformation {

    /**
     * 专业概况
     */
    private String majorSurvey;

    /**
     * 专业介绍
     */
    private String majorIntroduce;

    /**
     * 考研方向
     */
    private String directionOfPostgraduateEntranceExamination;

    /**
     * 主要课程
     */
    private String mainCourses;

    /**
     * 就业方向
     */
    private String employmentDirection;

    /**
     * 学校
     */
    private String school;
}
