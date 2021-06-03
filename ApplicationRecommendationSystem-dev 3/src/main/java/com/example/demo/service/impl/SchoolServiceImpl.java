package com.example.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONObjectCodec;
import com.example.demo.entity.neo4j.*;
import com.example.demo.repository.SchoolInfoRepository;
import com.example.demo.service.SchoolService;
import com.example.demo.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.ogm.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * @author 11834
 */
@Slf4j
@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private SchoolInfoRepository schoolInfoRepository;


    @Override
    public SchoolInfo getSchoolInfo(String name,String riskLevel,String minScore,int minRank) {
        Result result = schoolInfoRepository.getSchoolInfo(name);
        School school = null;
        String educationWay = "";
        SchoolLabel schoolLabel = null;
        String city = "";
        String province = "";
        String educationType = "";
        String collegeOwner = "";
        String introduction = "";
        boolean masterPoint = false;
        boolean doctorPoint = false;
        boolean laboratory = false;
        List<HashMap<String,String>> pictureList = new ArrayList<HashMap<String,String>>();
        Map<String,Object> map = null;
        try{
            map = result.iterator().next();
        }
        catch(Exception e){
            log.error("学校信息缺失：{}",name);
            result = null;
        }

        if(result != null && map != null){
            if(map.get("school") != null) {
                school = (School) map.get("school");
            }
            if(map.get("educationWay") != null) {
                educationWay = map.get("educationWay").toString();
            }
            if(map.get("schoolLabel") != null) {
                schoolLabel = (SchoolLabel) map.get("schoolLabel");
            }
            if(map.get("city") != null) {
                city = map.get("city").toString();
            }
            if(map.get("province") != null) {
                province = map.get("province").toString();
            }
            if(map.get("educationType") != null) {
                educationType = map.get("educationType").toString();
            }
            if(map.get("educationWay") != null) {
                educationWay = map.get("educationWay").toString();
            }
            if(map.get("collegeOwner") != null) {
                collegeOwner = map.get("collegeOwner").toString();
            }

            if(map.get("pictures") != null){
                JSONArray pictureArray = (JSONArray) JSONObject.parseObject(JSONObject.toJSONString(map.get("pictures")), Map.class).get("propertyList");
                for(int i=0;i<pictureArray.size();i++) {
                    JSONObject object = (JSONObject) pictureArray.get(i);
                    HashMap<String,String> pictureMap = new HashMap<String,String>();
                    pictureMap.put(object.get("key").toString(),object.get("value").toString());
                    pictureList.add(pictureMap);
                }
            }
        }
        List<String> laboratories = schoolInfoRepository.getSchoolLaboratoryByName(name);
        if(laboratories != null && laboratories.size() > 0){
            if(laboratories.get(0) != "\\"){
                laboratory = true;
            }
        }
        Result doctorMasterPointResult = schoolInfoRepository.getSchoolDoctorMasterPointByName(name);
        Map<String,Object> doctorMasterPointMap = null;
        try{
            doctorMasterPointMap = doctorMasterPointResult.iterator().next();
        }
        catch(Exception e){
            log.error("硕博士点信息缺失：{}",name);
            doctorMasterPointResult = null;
        }
        if(doctorMasterPointResult != null && doctorMasterPointMap != null){
            if(doctorMasterPointMap.get("masterPoint") != null && !(doctorMasterPointMap.get("masterPoint").toString().equals("/"))) {
                int masterPointTmp =  Integer.parseInt(doctorMasterPointMap.get("masterPoint").toString());
                if(masterPointTmp > 0){
                    masterPoint = true;
                }
            }
            if(doctorMasterPointMap.get("doctorPoint") != null && !(doctorMasterPointMap.get("doctorPoint").toString().equals("/"))) {
                int doctorPointTmp =  Integer.parseInt(doctorMasterPointMap.get("doctorPoint").toString());
                if(doctorPointTmp > 0){
                    doctorPoint = true;
                }
            }
        }
        List<String> admissionUrls = null;
        if(minRank == 0) {
            introduction = schoolInfoRepository.getSchoolIntroductionByName(name);
            admissionUrls = schoolInfoRepository.getSchoolAdmissionUrlByName(name);
        }

        SchoolInfo schoolInfo = new SchoolInfo();
        schoolInfo.setEducationWay(educationWay);
        schoolInfo.setSchool(school);
        schoolInfo.setSchoolLabel(schoolLabel);
        schoolInfo.setCity(city);
        schoolInfo.setProvince(province);
        schoolInfo.setEducationType(educationType);
        schoolInfo.setLaboratory(laboratory);
        schoolInfo.setDoctorPoint(doctorPoint);
        schoolInfo.setMasterPoint(masterPoint);
        schoolInfo.setRiskLevel(riskLevel);
        schoolInfo.setCollegeOwner(collegeOwner);
        schoolInfo.setMinRank(minRank);
        schoolInfo.setMinScore(minScore);
        schoolInfo.setIntroduction(introduction);
        schoolInfo.setImages(pictureList);
        schoolInfo.setAdmissionUrl(admissionUrls);
        return schoolInfo;
    }

    @Override
    public List<Major> getMajorList(String name) {
        return schoolInfoRepository.getMajorListBySchoolName(name);
    }

    @Override
    public List<SchoolAdmission> getSchoolAdmissionList(String name, String province, String subject, String level) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        List<SchoolAdmission> schoolAdmissions = new ArrayList<SchoolAdmission>();
        //6月份后按照当年数据往前推算，6月份前从去年数据往前推算
        if(month > 6){
            for(int i = year;i > year - 6;i--){
                String minAdmissionScore = schoolInfoRepository.getMinAdmissionScore(name,province,level,subject,Integer.toString(i));
                SchoolAdmission schoolAdmission= schoolInfoRepository.getAdmissionByMinScore(name,province,level,subject,Integer.toString(i),minAdmissionScore);
                schoolAdmissions.add(schoolAdmission);
            }
        }
        else{
            for(int i = year-1;i > year - 6;i--){
                String minAdmissionScore = schoolInfoRepository.getMinAdmissionScore(name,province,level,subject,Integer.toString(i));
                SchoolAdmission schoolAdmission= schoolInfoRepository.getAdmissionByMinScore(name,province,level,subject,Integer.toString(i),minAdmissionScore);
                schoolAdmissions.add(schoolAdmission);
            }
        }
        return schoolAdmissions;
    }

    @Override
    public List<MajorAdmission> getSchoolMajorAdmission(String name,String province,String subject,String year) {
        Result result = schoolInfoRepository.getMajoAdmission(name,province,year,subject);
        List<MajorAdmission> majorAdmissions = new ArrayList<MajorAdmission>();
        if(result != null){
            Iterator<Map<String, Object>> iterator = result.iterator();
            while(iterator.hasNext()) {
                MajorAdmission majorAdmission = new MajorAdmission();
                Map<String, Object> map = iterator.next();
                if (map.get("id") != null) {
                    majorAdmission.setId(map.get("id").toString());
                }
                if (map.get("majorName") != null) {
                    majorAdmission.setMajorName(map.get("majorName").toString());
                }
                if (map.get("minScore") != null) {
                    majorAdmission.setMinScore(map.get("minScore").toString());
                }
                if (map.get("minRank") != null) {
                    majorAdmission.setMinRank(map.get("minRank").toString());
                }
                if (map.get("aveScore") != null) {
                    majorAdmission.setAveScore(map.get("aveScore").toString());
                }
                if (map.get("layer") != null) {
                    majorAdmission.setLayer(map.get("layer").toString());
                }
                majorAdmissions.add(majorAdmission);
            }
        }

        return majorAdmissions;
    }

    @Override
    public List<SchoolInfo> getSchoolInfoListReport(int year,String province,int rank, int floatRank, String subject, String mode) {
        int maxRank = rank + floatRank;
        int minRank = rank - floatRank;
        String riskLevel = "";
        Result result = null;
        List<SchoolInfo> schoolInfos = new ArrayList<>();
        List<String> schoolNames = new ArrayList<>();
        HashMap<String,Integer> schoolRank = new HashMap<>();
        if(!subject.isEmpty()) {
            result = schoolInfoRepository.getSchoolByScoreRank(String.valueOf(year),province, maxRank, minRank, subject, mode);
        }
        else{
            result = schoolInfoRepository.getSchoolByScoreRankNoSubject(String.valueOf(year),province, maxRank, minRank, mode);
        }
        if(result != null){
            List<String> schools = new ArrayList<>();
            Iterator<Map<String, Object>> iterator = result.iterator();
            while(iterator.hasNext()) {
                Map<String, Object> map = iterator.next();
                String schoolName = "";
                if (map.get("name") != null) {
                    schoolName = map.get("name").toString();
                    if(schools.contains(schoolName)){
                        continue;
                    }
                    else{
                        schools.add(schoolName);
                    }
                }
                String minScore = "";
                if (map.get("minScore") != null) {
                    minScore = map.get("minScore").toString();
                }
                int schoolMinRank = 0;
                if (map.get("minRank") != null && !(map.get("minRank").toString().equals("/"))) {
                    schoolMinRank = Integer.parseInt(map.get("minRank").toString());
                    if(rank - floatRank / 3 >= schoolMinRank){
                        riskLevel = "冲";
                    }
                    else if(rank +  floatRank / 3 > schoolMinRank && rank -  floatRank / 3 < schoolMinRank){
                        riskLevel = "稳";
                    }
                    else{
                        riskLevel = "保";
                    }
                }

                SchoolInfo schoolInfo = getSchoolInfo(schoolName,riskLevel,minScore,schoolMinRank);
                if(schoolInfo.getSchool() != null){
                    schoolInfos.add(schoolInfo);
                }
            }
        }
        log.info("school names length:{}",schoolInfos.size());
        return schoolInfos;
    }
}
