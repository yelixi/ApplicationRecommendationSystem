package com.example.demo.entity.neo4j;

import lombok.Data;
import org.neo4j.ogm.annotation.*;

import java.io.Serializable;

/**
 * @author 11834
 */
@Data
public class MajorAdmission implements Serializable {


    /**
     * 专业id
     */
    private String id;
    /**
     * 专有名称
     */
    private String majorName = "";
    /**
     * 专业最低分
     */
    private String minScore = "";
    /**
     * 专业最低排名
     */
    private String minRank = "";
    /**
     * 专业平均分
     */
    private String aveScore = "";
    /**
     * 专业批次
     */
    private String layer = "";




}
