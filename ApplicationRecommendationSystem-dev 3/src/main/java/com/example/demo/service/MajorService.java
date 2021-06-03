package com.example.demo.service;

import com.example.demo.entity.neo4j.CountryMajor;
import com.example.demo.entity.neo4j.Major;

import java.util.List;
import java.util.Map;

/**
 * Created by 林夕
 * Date 2021/5/10 10:14
 */
public interface MajorService {

    /**
     * 专业大类获取专业小类
     * @param category
     * @param undergraduate
     * @return
     */
    List<String> getMajorCategory(String category,boolean undergraduate);

    /**
     * 根据专业小类获取具体专业
     * @param smallCategory
     * @param undergraduate
     * @return
     */
    List<CountryMajor> getMajorSmallCategory(String smallCategory, boolean undergraduate);

    /**
     * 获取专业卡片
     * @param name
     * @return
     */
    Major getMajorCard(String name);


    /**
     * 通过国家专业小类获取学校专业
     * @param majors
     * @param undergraduate
     * @return
     */
    List<String> getSchoolMajorsBySmallClasses(List<String> majors,boolean undergraduate);


    /**
     * 通过专业名称获取对应学校的专业
     * @param schoolName
     * @param majorNames
     * @param limit
     * @return
     */
    List<String> getMajorByMajorClasses(String schoolName, List<String> majorNames, int limit);

    /**
     * 通过专业名称获取对应学校的专业
     * @param schoolName
     * @param majorName
     * @param limit
     * @return
     */
    List<String> getMajorByMajorClass(String schoolName, String majorName, int limit);
}
