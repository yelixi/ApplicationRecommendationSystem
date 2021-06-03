package com.example.demo.entity.neo4j;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

/**
 * @author 11834
 */
@Data
@NodeEntity(label = "CountryMajor")
public class CountryMajor {

    @Id
    @GeneratedValue
    private Long identity;

    /**
     * 专业介绍
     */
    @Property(name = "countrymajor_introducation")
    private String introduction;

    /**
     * 专业课程
     */
    @Property(name = "study_subject")
    private String subject;

    /**
     * 专业名称
     */
    @Property(name = "countrymajor_name")
    private String name;

    /**
     * 学制
     */
    @Property(name = "study_years")
    private String studyYears;

    /**
     * 专业代码
     */
    @Property(name = "major_code")
    private String code;

    /**
     * 学位类型
     */
    @Property(name = "degree")
    private String degree;

    /**
     * 工作方向
     */
    @Property(name = "job_direction")
    private String jobDirection;

    /**
     * 学位层次：本科 or 专科
     */
    @Property(name = "education_layer")
    private String educationLayer;



}
