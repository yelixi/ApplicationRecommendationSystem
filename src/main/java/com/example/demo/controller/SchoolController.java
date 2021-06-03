package com.example.demo.controller;


import com.example.demo.entity.*;
import com.example.demo.enums.UnifiedExaminationArea;
import com.example.demo.service.SchoolService;
import com.example.demo.service.UserInfoService;
import com.example.demo.util.CommUtil;
import com.example.demo.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/school")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 获取学校简介等信息
     *
     * @param name
     * @return
     */
    @RequestMapping("/getBrief")
    public SchoolInfo getSchoolInfo(@RequestParam("name") String name) {
        return schoolService.findSchoolInfo(name);
    }


    /**
     * 获取学校招生计划信息
     *
     * @param name
     * @return
     */
    @RequestMapping("/getSchoolAdmission")
    public List<SchoolAdmission> getSchoolAdmission(@RequestParam("name") String name) {
        return schoolService.getSchoolAdmissionBySchoolName(name);
    }

    /**
     * 获取专业招生计划信息
     *
     * @param name
     * @return
     */
    @RequestMapping("/getMajorAdmission")
    public List<MajorAdmission> getMajorAdmission(@RequestParam("name") String name) {
        return null;
        //    return schoolService.getMajorBySchoolName(name);
    }


    /**
     * 根据学校名称获取专业列表
     *
     * @param name
     * @return
     */
    @RequestMapping("/getMajorList")
    public List<String> getMajorList(@RequestParam("name") String name) {
        return null;
        //    return schoolService.getMajorListBySchoolName(name);
    }

    /**
     * 根据学校和专业名称获取专业详情
     *
     * @param map
     * @return
     */
    @RequestMapping("/getMajorInfo")
    public MajorInfo getMajorList(@RequestBody Map<String, Object> map) {
        String schoolName = map.get("schoolName").toString();
        String majorName = map.get("majorName").toString();
        return null;
        //    return schoolService.getMajorInfo(schoolName, majorName);
    }

    /**
     * 一键填报
     * @param map
     * @return
     */
    @RequestMapping("/report")
    public List<SchoolInfo> reportVoluntary(@RequestBody Map<String, Object> map) {
        String province = null;
        String subject = null;
        String userName = "";
        int year = 0;
        int rank = 0;
        float chinese = 0;
        float math = 0;
        float english = 0;
        float chemistry = 0;
        float biology = 0;
        float politics = 0;
        float history = 0;
        float geography = 0;
        float technology = 0;
        float comprehensiveScience = 0;
        float comprehensiveLiberalArts = 0;
        float totalScore = 0;
        HashMap<String, Float> validScore = new HashMap<String, Float>();

        if (map.get("province") != null) {
            province = map.get("province").toString();
        }
        if (map.get("userName") != null) {
            userName = map.get("userName").toString();
        }
        if (map.get("year") != null) {
            year = Integer.parseInt(map.get("year").toString());
        }

        if (map.get("subject") != null) {
            subject = map.get("subject").toString();
        }
        //如果rank有非0值，则是高考成绩带有排名；否则是模拟考试成绩
        if (map.get("rank") != null) {
            rank = Integer.parseInt(map.get("rank").toString());
        }
        if (map.get("chinese") != null) {
            chinese = Float.parseFloat(map.get("chinese").toString());
            validScore.put("chinese", chinese);
        }
        if (map.get("math") != null) {
            math = Float.parseFloat(map.get("math").toString());
            validScore.put("math", math);
        }
        if (map.get("english") != null) {
            english = Float.parseFloat(map.get("english").toString());
            validScore.put("english", english);
        }
        if (map.get("chemistry") != null) {
            chemistry = Float.parseFloat(map.get("chemistry").toString());
            validScore.put("chemistry", chemistry);
        }
        if (map.get("biology") != null) {
            biology = Float.parseFloat(map.get("biology").toString());
            validScore.put("biology", biology);
        }
        if (map.get("politics") != null) {
            politics = Float.parseFloat(map.get("politics").toString());
            validScore.put("politics", politics);
        }
        if (map.get("history") != null) {
            history = Float.parseFloat(map.get("history").toString());
            validScore.put("history", history);
        }
        if (map.get("geography") != null) {
            geography = Float.parseFloat(map.get("geography").toString());
            validScore.put("geography", geography);
        }
        if (map.get("technology") != null) {
            technology = Float.parseFloat(map.get("technology").toString());
            validScore.put("technology", technology);
        }
        if (map.get("comprehensiveScience") != null) {
            comprehensiveScience = Float.parseFloat(map.get("comprehensiveScience").toString());
            validScore.put("comprehensiveScience", comprehensiveScience);
        }
        if (map.get("comprehensiveLiberalArts") != null) {
            comprehensiveLiberalArts = Float.parseFloat(map.get("comprehensiveLiberalArts").toString());
            validScore.put("comprehensiveLiberalArts", comprehensiveLiberalArts);
        }
        if (map.get("totalScore") != null) {
            totalScore = Float.parseFloat(map.get("totalScore").toString());
        }
        //根据用户名去查地区
        UserInfo userInfo = userInfoService.queryLocationByUserName(userName);
        //1、如果是模拟考，根据总分获取排名(分文理科)
        if (rank == 0) {
            rank = schoolService.getRankByScore(province, totalScore, subject, year - 1);
        }

        /**
         *  2、判断所属高考区
         *  (1)统考区（已知往年文理科招生数据）
         *  (2)2021新高考改革地区（未知选科后的数据）（广东、福建、河北、辽宁、江苏、湖南、湖北、重庆）
         *  (3)2021新高考改革地区（已知选科后的数据）（上海、浙江、北京、海南、山东、天津）：
         */
        int areaNum = CommonUtil.examArea(province);
        List<SchoolInfo> schoolInfoList = null;
        if (areaNum == CommUtil.UNKNOW_NEW_EXAMINATION_AREA) {
            if (province.equals(CommUtil.JIANGSU_PROVINCE)) {
                totalScore = totalScore / 750 * 480;
            }
            //查询去年该分数对应的文科名次
            int rankArts = schoolService.getRankLastYear(CommUtil.LIKE_SUBJECT, year - 1, totalScore);
            //查询去年该分数对应的理科名次
            int rankScience = schoolService.getRankLastYear(CommUtil.WENKE_SUBJECT, year - 1, totalScore);
            if (CommUtil.WENKE_SUBJECT.equals(subject)) {
                rank = rank * rankArts / (rankArts + rankScience);
            } else {
                rank = rank * rankScience / (rankArts + rankScience);
            }
        }
        if (areaNum == CommUtil.UNIFIED_EXAMINATION_AREA) {
            schoolInfoList = schoolService.getSchoolInfoListReport(rank + 5000, rank - 5000, subject, userInfo.getMode());
        } else {
            schoolInfoList = schoolService.getSchoolInfoListReport(rank + 5000, rank - 5000, "",userInfo.getMode());
        }
        //    return schoolInfoList;
        return null;

    }


    /**
     * 一键生成
     * @param map
     * @return
     */
    @RequestMapping("/exportReport")
    public List<SchoolInfo> exportReportVoluntary(@RequestBody Map<String, Object> map) {
        String schoolString = "";
        String majorString = "";
        String userName = "";

        List<String> schoolNameList = null;
        if (map.get("schoolList") != null) {
            schoolString = map.get("schoolList").toString();
            schoolNameList = Arrays.asList(schoolString.split(","));
        }

        List<String> majorList = null;
        if (map.get("major") != null) {
            majorString = map.get("major").toString();
            majorList = Arrays.asList(majorString.split(","));
        }

        if (map.get("userName") != null) {
            userName = map.get("userName").toString();
        }

        /**
         List<String> cityList = null;
         if (map.get("city") != null) {
         cityString = map.get("city").toString();
         cityList = Arrays.asList(cityString.split(","));
         }

         if(map.get("phdPoint") != null){
         phdPoint = Boolean.parseBoolean(map.get("phdPoint").toString());
         }

         if(map.get("masterPoint") != null){
         masterPoint = Boolean.parseBoolean(map.get("masterPoint").toString());
         }*/
        //根据用户名去查地区
        UserInfo userInfo = userInfoService.queryLocationByUserName(userName);
        String province = userInfo.getLocation();
        String mode = userInfo.getMode();
        //根据学校名称去查询专业组
        HashMap<String,List<String>> schoolMajorMap = new HashMap<String,List<String>>();
        HashMap<String,HashMap<String,List<String>>>  schoolMajorGroupMap= new HashMap<String,HashMap<String,List<String>>>();
        int areaNum = CommonUtil.examArea(userInfo.getLocation());
        int groupNum = 0;
        int majorLimit = 0;
        //1.统考区（吉林、黑龙江、内蒙古、山西、河南、安徽、江西、广西、四川、云南、贵州、陕西、宁夏、甘肃、青海、新疆、西藏）
        //9个院校*6个专业
        if (areaNum == CommUtil.UNKNOW_NEW_EXAMINATION_AREA) {
            schoolMajorMap = queryMajor(schoolNameList,majorList,6);
        }
        //2、院校专业组志愿
        else{
            switch(province){
                //(1)江苏 本科：40个院校专业组*6个专业志愿 专科：40个院校专业组*6个专业志愿
                case "江苏"://(5)福建 本科：40个院校专业组志愿*6个专业 专科：40个院校专业组志愿*6个专业
                case "福建":
                    groupNum = 40;
                    majorLimit = 6;
                    break;
                //(2)湖北 本科：45个院校专业组志愿*6个专业
                case "湖北":
                    groupNum = 45;
                    majorLimit = 6;
                    break;
                //(3)湖南 本科：45个院校专业组志愿*6个专业 专科：30个院校专业组志愿*6个专业
                case "湖南":
                    if("本科".equals(mode)) {
                        groupNum = 45;
                    }else{
                        groupNum = 30;
                    }
                    majorLimit = 6;
                    break;
                //(4)广东 本科：45个院校专业组志愿*6个专业 专科：25个院校专业组志愿*6个专业
                case "广东":
                    if("本科".equals(mode)) {
                        groupNum = 45;
                    }else{
                        groupNum = 25;
                    }
                    majorLimit = 6;
                    break;
                //(6)上海 本科：24个院校专业组志愿*6个专业 专科：8个院校专业组志愿*6个专业
                case "上海":
                    if("本科".equals(mode)) {
                        groupNum = 24;
                    }else{
                        groupNum = 8;
                    }
                    majorLimit = 6;
                    break;
                //(7)海南 本科：24个院校专业组志愿*6个专业 专科：10个院校专业组志愿*6个专业
                case "海南":
                    if("本科".equals(mode)) {
                        groupNum = 24;
                    }else{
                        groupNum = 10;
                    }
                    majorLimit = 6;
                    break;
                //(8)天津 本科：50个院校专业组志愿*6个专业
                case "天津":
                    groupNum = 50;
                    majorLimit = 6;
                    break;
                //(9)北京 本科：16个院校专业组志愿*6个专业
                case "北京":
                    groupNum = 16;
                    majorLimit = 6;
                    break;
                //3、专业+院校
                //(1)浙江 本科：80个专业志愿
                case "浙江":
                    majorLimit = 80;
                    break;
                //(2)山东 本科：96个专业志愿
                case "山东":
                    //(3)河北 本科：96个专业志愿 专科96个专业志愿
                case "河北":
                    //(4)重庆 本科：96个专业志愿 专科：96个专业志愿
                case "重庆":
                    majorLimit = 96;
                    break;
                //(5)辽宁 本科：112个专业志愿 专科：60个专业志愿
                case "辽宁":
                    majorLimit = 112;
                    break;
            }
            schoolMajorGroupMap = queryMajorGroup(schoolNameList,majorList,majorLimit,groupNum);
        }
        List<SchoolInfo> schoolInfoList = new ArrayList<SchoolInfo>();
        if(schoolMajorMap != null && schoolMajorMap.size() > 0){
            for(Map.Entry<String, List<String>> entry : schoolMajorMap.entrySet()){
                SchoolInfo schoolInfo = new SchoolInfo();
                School school = new School();
                school.setSchoolCode((entry.getKey()));
                schoolInfo.setSchool(school);
                schoolInfo.setMajor(entry.getValue());
                schoolInfoList.add(schoolInfo);
            }
        }
        if(schoolMajorGroupMap.size() > 0){
            for(Map.Entry<String,HashMap<String,List<String>>> entry : schoolMajorGroupMap.entrySet()){
                SchoolInfo schoolInfo = new SchoolInfo();
                School school = new School();
                school.setSchoolCode((entry.getKey()));
                schoolInfo.setSchool(school);
                List<MajorGroup> majorGroupList = new ArrayList<MajorGroup>();
                for(Map.Entry<String, List<String>> entry1 : entry.getValue().entrySet()){
                    MajorGroup majorGroup = new MajorGroup();
                    majorGroup.setMajors(entry1.getValue());
                    majorGroupList.add(majorGroup);
                }
                schoolInfoList.add(schoolInfo);
            }
        }


        return null;
        //    return schoolInfoList;
    }


    public HashMap<String,List<String>> queryMajor(List<String> schoolNameList,List<String> majorList,int limit){
        HashMap<String,List<String>> schoolMajorMap = new HashMap<String,List<String>>();
        for(int i=0;i < schoolNameList.size();i++){
            List<String> majorNameList = schoolService.getMajorByCodes(schoolNameList.get(i),majorList,limit);
            schoolMajorMap.put(schoolNameList.get(i),majorNameList);
        }
        return schoolMajorMap;
    }

    public HashMap<String,HashMap<String,List<String>>> queryMajorGroup(List<String> schoolNameList,List<String> majorList,int majorLimit,int groupLimit){
        HashMap<String,HashMap<String,List<String>>> schoolMajorMap = new  HashMap<String,HashMap<String,List<String>>>();
        for(int i=0;i < schoolNameList.size();i++){
            HashMap<String,List<String>> majorGroupMap = new HashMap<String,List<String>>();
            List<String> majorNameGroupList = new ArrayList<String>();
            for(int j=0;j<majorList.size();j++) {
                majorNameGroupList = schoolService.getMajorByCode(schoolNameList.get(i), majorList.get(j), majorLimit);
                majorGroupMap.put(majorList.get(j), majorNameGroupList);
            }
            schoolMajorMap.put(schoolNameList.get(i),majorGroupMap);
        }

        if(groupLimit > 40){
            //Todo:取40个
        }

        return schoolMajorMap;
    }





}
