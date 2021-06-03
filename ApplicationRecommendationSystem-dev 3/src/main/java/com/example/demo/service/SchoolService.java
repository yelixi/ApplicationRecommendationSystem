package com.example.demo.service;


import com.example.demo.entity.neo4j.*;
import org.neo4j.ogm.model.Result;

import java.util.HashMap;
import java.util.List;

/**
 * @author 11834
 */
public interface SchoolService {

    /**
     * 根据学校名称，获取学校信息
     * @param name
     * @param riskLevel
     * @param minScore
     * @param minRank
     * @return
     */
    SchoolInfo getSchoolInfo(String name, String riskLevel,String minScore,int minRank);


    /**
     * 根据学校名称获取专业列表
     * @param name
     * @return
     */
    List<Major> getMajorList(String name);


    /**
     * 查询学校招生计划
     * @param name
     * @param province
     * @param level
     * @param subject
     * @return
     */
    List<SchoolAdmission> getSchoolAdmissionList(String name,String province,String level,String subject);


    /**
     * 获取学校专业招生计划
     * @param name
     * @param province
     * @param level
     * @param subject
     * @return
     */
    List<MajorAdmission> getSchoolMajorAdmission(String name,String province,String level,String subject);


    /**
     *
     * 根据省份排名获取排名在上下浮动5000名的学校
     * @param province
     * @param rank
     * @param floatRang
     * @param subject
     * @param mode
     * @param year
     * @return
     */
    List<SchoolInfo> getSchoolInfoListReport(int year,String province,int rank, int floatRang, String subject,String mode);
}
