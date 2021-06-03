package com.example.demo.controller;

import com.example.demo.entity.UserInfo;
import com.example.demo.entity.neo4j.School;
import com.example.demo.entity.neo4j.SchoolInfo;
import com.example.demo.enums.MajorGroup;
import com.example.demo.model.RestResult;
import com.example.demo.service.MajorService;
import com.example.demo.service.SchoolService;
import com.example.demo.service.UserInfoService;
import com.example.demo.service.VolunteerService;
import com.example.demo.util.CommUtil;
import com.example.demo.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.*;

/**
 * @author 11834
 */
@Slf4j
@RestController
@RequestMapping("/volunteer")
public class VolunteerController {

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private MajorService majorService;

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 一键填报
     * @param map
     * @return
     */
    @RequestMapping("/report")
    public RestResult<List<SchoolInfo>> reportVoluntary(@RequestBody Map<String, Object> map) {
        String province = "";
        String subject = "";
        int openId = 0;
        int year = 0;
        int rank = 0;
        int chinese = 0;
        int math = 0;
        int english = 0;
        int chemistry = 0;
        int biology = 0;
        int politics = 0;
        int physical = 0;
        int history = 0;
        int geography = 0;
        int technology = 0;
        int comprehensiveScience = 0;
        int comprehensiveLiberalArts = 0;
        int totalScore = 0;
        HashMap<String, String> validScore = new HashMap<String, String>();
        UserInfo userInfo = new UserInfo();
        if (map.get("province") != null) {
            province = map.get("province").toString();
            userInfo.setLocation(province);
        }
        if (map.get("openId") != null) {
            openId = Integer.parseInt(map.get("openId").toString());
            userInfo.setUserId(openId);
        }
        if (map.get("year") != null) {
            year = Integer.parseInt(map.get("year").toString());
            userInfo.setYear(year);
        }
        //如果rank有非0值，则是高考成绩带有排名；否则是模拟考试成绩
        if (map.get("rank") != null && !"".equals(map.get("rank").toString())) {
            rank = Integer.parseInt(map.get("rank").toString());
            userInfo.setRank(rank);
        }
        if (map.get("chinese") != null) {
            chinese = Integer.parseInt(map.get("chinese").toString());
            userInfo.setChinese(chinese);
        }
        if (map.get("math") != null) {
            math = Integer.parseInt(map.get("math").toString());
            userInfo.setMath(math);
        }
        if (map.get("english") != null) {
            english = Integer.parseInt(map.get("english").toString());
            userInfo.setEnglish(english);
        }
        if (map.get("chemistry") != null) {
            chemistry = Integer.parseInt(map.get("chemistry").toString());
            userInfo.setChemistry(chemistry);
        }
        if (map.get("biology") != null) {
            biology = Integer.parseInt(map.get("biology").toString());
            userInfo.setBiology(biology);
        }
        if (map.get("politics") != null) {
            politics = Integer.parseInt(map.get("politics").toString());
            userInfo.setPolitics(politics);
        }
        if (map.get("history") != null) {
            history = Integer.parseInt(map.get("history").toString());
            userInfo.setHistory(history);
            subject = "文科";
        }
        if (map.get("physical") != null) {
            physical = Integer.parseInt(map.get("physical").toString());
            userInfo.setPhysics(physical);
            subject = "理科";
        }
        userInfo.setSubject(subject);
        if (map.get("geography") != null) {
            geography = Integer.parseInt(map.get("geography").toString());
            userInfo.setGeography(geography);
        }
        if (map.get("technology") != null) {
            technology = Integer.parseInt(map.get("technology").toString());
            userInfo.setTechnology(technology);
        }
        if (map.get("comprehensiveScience") != null) {
            comprehensiveScience = Integer.parseInt(map.get("comprehensiveScience").toString());
            userInfo.setComprehensiveScience(comprehensiveScience);
        }
        if (map.get("comprehensiveLiberalArts") != null) {
            comprehensiveLiberalArts = Integer.parseInt(map.get("comprehensiveLiberalArts").toString());
            userInfo.setComprehensiveLiberalArts(comprehensiveLiberalArts);
        }
        if (map.get("totalScore") != null) {
            totalScore = Integer.parseInt(map.get("totalScore").toString());
            userInfo.setTotalScore(totalScore);

        }
        List<SchoolInfo> schoolInfoList = null;
        try {
            //根据用户名去查地区
            UserInfo userInfo1 = userInfoService.queryByUserId(openId);
            if(userInfo1 == null){
                //ToDo：插入userInfo
                userInfoService.insert(userInfo);
            }
            else{
                //ToDo:更新userInfo
                userInfo.setLocation(userInfo1.getLocation());
                userInfo.setMode(userInfo1.getMode());
                userInfoService.update(userInfo);
            }
            String mode = userInfo1.getMode();
            //1、如果是模拟考，根据总分获取排名(分文理科),统考区（已知往年文理科招生数据）
            if (rank == 0) {
                Calendar cal = Calendar.getInstance();
                int month = cal.get(Calendar.MONTH);
                if (month < 7) {
                    year = year - 1;
                }
                if (!(province.equals(CommUtil.JIANGSU_PROVINCE) && year <= 2021)) {
                    rank = volunteerService.getRankByScore(province, totalScore, subject, year);
                }
            }
            /**
             *  2、判断所属高考区
             *  (1)统考区（已知往年文理科招生数据）
             *  (2)2021新高考改革地区（未知选科后的数据）（广东、福建、河北、辽宁、江苏、湖南、湖北、重庆）
             *  (3)2021新高考改革地区（已知选科后的数据）（上海、浙江、北京、海南、山东、天津）：
             */
            int areaNum = CommonUtil.examArea(province);
            if (areaNum == CommUtil.UNKNOW_NEW_EXAMINATION_AREA) {
                if (province.equals(CommUtil.JIANGSU_PROVINCE)) {
                    totalScore = totalScore * 480 / 750;
                }
                //查询去年该分数对应的文科名次
                int rankArts = volunteerService.getRankByScore(province, totalScore, CommUtil.WENKE_SUBJECT, year);
                //查询去年该分数对应的理科名次
                int rankScience = volunteerService.getRankByScore(province, totalScore, CommUtil.LIKE_SUBJECT, year);
                System.out.println("文科排名：" + rankArts);
                System.out.println("理科排名：" + rankScience);
                if (rank > 0) {
                    if (CommUtil.WENKE_SUBJECT.equals(subject)) {
                        rank = rank * rankArts / (rankArts + rankScience);
                    } else {
                        rank = rank * rankScience / (rankArts + rankScience);
                    }
                } else {
                    if (CommUtil.WENKE_SUBJECT.equals(subject)) {
                        rank = rankArts;
                    } else {
                        rank = rankScience;
                    }
                }
            }
            if (areaNum == CommUtil.UNIFIED_EXAMINATION_AREA) {
                schoolInfoList = schoolService.getSchoolInfoListReport(year, province, rank, 5000, subject, mode);
            } else {
                if (year == 2020) {
                    schoolInfoList = schoolService.getSchoolInfoListReport(year, province, rank, 5000, subject, mode);
                } else {
                    schoolInfoList = schoolService.getSchoolInfoListReport(year, province, rank, 5000, "", mode);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            return RestResult.error("未查到符合条件的数据");
        }
        return RestResult.success(schoolInfoList);
    }


    /**
     * 一键生成
     * @param map
     * @return
     */
     @RequestMapping("/exportReport")
     public RestResult<HashMap<String,List<String>>> exportReportVoluntary(@RequestBody Map<String, Object> map) {
     String schoolString = "";
     String majorString = "";
     String userName = "";

     List<String>  schoolMajorNameList = null;
     List<String> schoolNameList = null;
     if (map.get("schools") != null) {
        schoolString = map.get("schools").toString();
        schoolNameList = Arrays.asList(schoolString.split(","));

     }
     if (map.get("userName") != null) {
        userName = map.get("userName").toString();
     }
     //根据用户名去查地区
  /**   UserInfo userInfo = userInfoService.queryLocationByUserName(userName);
     String province = userInfo.getLocation();
     String mode = userInfo.getMode();*/
     String province = "江苏";
     String mode = "本科";
     boolean undergraduate = true;
     if (!"本科".equals(mode)) {
         undergraduate = false;
     }

     List<String> majorList = null;
     if (map.get("majors") != null) {
         majorString = map.get("majors").toString();
         majorList = Arrays.asList(majorString.split(","));
         if(majorList != null && majorList.size() > 0){
             schoolMajorNameList = majorService.getSchoolMajorsBySmallClasses(majorList,undergraduate);
         }
     }
     //根据学校名称去查询专业组
     HashMap<String,List<String>> schoolMajorMap = new HashMap<String,List<String>>();
     HashMap<String,HashMap<String,List<String>>>  schoolMajorGroupMap= new HashMap<String,HashMap<String,List<String>>>();
     int areaNum = CommonUtil.examArea(province);
     int groupNum = 0;
     int majorLimit = 0;
     //1.统考区（吉林、黑龙江、内蒙古、山西、河南、安徽、江西、广西、四川、云南、贵州、陕西、宁夏、甘肃、青海、新疆、西藏）
     //9个院校*6个专业
     if (areaNum == CommUtil.UNKNOW_NEW_EXAMINATION_AREA) {
        schoolMajorMap = queryMajor(schoolNameList,schoolMajorNameList,6);
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
     HashMap<String,Object> SchoolMajorMap = new HashMap<String,Object>();
     List<SchoolInfo> schoolInfoList = new ArrayList<SchoolInfo>();
     if(schoolMajorMap != null && schoolMajorMap.size() > 0){
         for(Map.Entry<String, List<String>> entry : schoolMajorMap.entrySet()){
             SchoolInfo schoolInfo = new SchoolInfo();
             School school = new School();
             school.setCode((entry.getKey()));
             schoolInfo.setSchool(school);
         //    schoolInfo.setMajor(entry.getValue());
             schoolInfoList.add(schoolInfo);
         }
     }
     if(schoolMajorGroupMap.size() > 0){
     for(Map.Entry<String,HashMap<String,List<String>>> entry : schoolMajorGroupMap.entrySet()){
         SchoolInfo schoolInfo = new SchoolInfo();
         School school = new School();
         school.setCode((entry.getKey()));
         schoolInfo.setSchool(school);
         List<MajorGroup> majorGroupList = new ArrayList<MajorGroup>();
         for(Map.Entry<String, List<String>> entry1 : entry.getValue().entrySet()){
          //   MajorGroup majorGroup = new MajorGroup();
          //   majorGroup.setMajors(entry1.getValue());
             //  majorGroupList.add(majorGroup);
        }
         schoolInfoList.add(schoolInfo);
         }
     }


     return RestResult.success(schoolMajorMap);
     }

    public HashMap<String,List<String>> queryMajor(List<String> schoolNameList,List<String> majorList,int limit){
        HashMap<String,List<String>> schoolMajorMap = new HashMap<String,List<String>>();
        for(int i=0;i < schoolNameList.size();i++){
            List<String> majorNameList = majorService.getMajorByMajorClasses(schoolNameList.get(i),majorList,limit);
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
                majorNameGroupList = majorService.getMajorByMajorClass(schoolNameList.get(i), majorList.get(j), majorLimit);
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
