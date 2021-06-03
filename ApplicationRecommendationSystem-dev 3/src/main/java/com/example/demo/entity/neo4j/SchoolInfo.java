package com.example.demo.entity.neo4j;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.neo4j.annotation.QueryResult;


/**
 * @author 11834
 */
@Data
public class SchoolInfo implements Serializable {


    private School school;
    private SchoolLabel schoolLabel;
    private String educationWay;
    private String educationType;
    private String province;
    private String city;
    private boolean masterPoint;
    private boolean doctorPoint;
    private boolean laboratory;
    private int minRank;
    private String minScore;
    private String riskLevel;
    private String collegeOwner;
    private String introduction;
    private List<String> admissionUrl;
    private List<HashMap<String,String>> images;
}
