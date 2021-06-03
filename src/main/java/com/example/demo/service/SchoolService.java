package com.example.demo.service;

import com.example.demo.entity.*;

import java.util.List;

public interface SchoolService {

    /**
     * 根据学校名称，获取学校信息
     * @param name
     * @return
     */
    SchoolInfo findSchoolInfo(String name);

    School findSchoolBrief(String name);

    /**
     * 通过学校名称获取近五年招生计划
     * @param name
     * @return
     */
    List<SchoolAdmission> getSchoolAdmissionBySchoolName(String name);


    /**
     * 通过学校名称获取个专业近五年招生计划
     * @param name
     * @return
     */
    List<MajorAdmission> getMajorBySchoolName(String name);


    /**
     * 通过学校名称获取学校专业列表
     * @param name
     * @return
     */
    List<String> getMajorListBySchoolName(String name);


    /**
     * 获取专业信息
     * @param schoolName
     * @param majorName
     * @return
     */
    MajorInfo getMajorInfo(String schoolName,String majorName);

    /**
     * 根据总分获取所在省份的排名
     * @param totalScore
     * @return
     */
    int getRankByScore(String province, float totalScore,String subject, int year);


    /**
     *
     * 根据省份排名获取排名在上下浮动5000名的学校
     *
     * @param maxRank
     * @param minRank
     * @param subject
     * @param mode
     * @return
     */
    List<SchoolInfo> getSchoolInfoListReport(int maxRank, int minRank, String subject,String mode);

    /**
     * 根据分数获取去年的文理科排名
     * @param subject
     * @param year
     * @param score
     * @return
     */
    Integer getRankLastYear(String subject,int year,float score);

    /**
     * 通过专业代码获取对应学校的专业
     * @param schoolName
     * @param codes
     * @param limit
     * @return
     */
    List<String> getMajorByCodes(String schoolName, List<String> codes, int limit);

    /**
     * 通过专业代码获取对应学校的专业
     * @param schoolName
     * @param code
     * @param limit
     * @return
     */
    List<String> getMajorByCode(String schoolName, String code, int limit);
}
