package com.example.demo.entity.neo4j;

import org.neo4j.ogm.annotation.Property;
import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import java.io.Serializable;

/**
 * @author 11834
 */
@Data
@NodeEntity(label="Admission_Information")
public class SchoolAdmission implements Serializable {

    @Id
    @GeneratedValue
    private Long identity;

    private String id;
    /**
     * 录取年份
     */
    @Property(name = "admission_year")
    private String year;
    /**
     * 录取最低分
     */
    @Property(name = "minimum_score")
    private String mimScore;
    /**
     * 省控线
     */
    @Property(name = "province_score")
    private String provinceScore;
    /**
     * 最低分排名
     */
    @Property(name = "minimum_rank")
    private String minScoreRank;
    /**
     * 招生批次
     */
    @Property(name = "admission_layer")
    private String layer;

    /**
     * 省份
     */
    @Property(name = "admission_province")
    private String province;

    /**
     * 平均分
     */
    @Property(name = "average_score")
    private String aveScore;

}
