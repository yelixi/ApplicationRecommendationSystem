package com.example.demo.controller;

import com.example.demo.entity.neo4j.Major;
import com.example.demo.entity.neo4j.MajorAdmission;
import com.example.demo.entity.neo4j.SchoolAdmission;
import com.example.demo.entity.neo4j.SchoolInfo;
import com.example.demo.model.RestResult;
import com.example.demo.service.SchoolService;
import com.example.demo.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * @author 11834
 */
@Slf4j
@RestController
@RequestMapping("/school")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 获取学校基本信息
     * @param name
     * @return
     */
    @RequestMapping("/getSchoolInfo")
    public RestResult<SchoolInfo> getSchoolInfo(@RequestParam("name") String name) {
        log.info("[arg]receive request: getSchoolInfo");
        SchoolInfo schoolInfo = null;
        try {
            schoolInfo = schoolService.getSchoolInfo(name,"","",0);
        }
        catch(Exception e){
            e.printStackTrace();
            return RestResult.error("get school info fail");
        }
        return RestResult.success(schoolInfo);
    }

    /**
     * 获取学校基本信息
     * @param name
     * @return
     */
    @RequestMapping("/getMajorList")
    public RestResult<List<Major>> getSchoolMajorList(@RequestParam("name") String name) {
        log.info("[arg]receive request: getMajorList");
        List<Major> majorList = null;
        try {
            majorList = schoolService.getMajorList(name);
        }
        catch(Exception e){
            e.printStackTrace();
            return RestResult.error("get school major list fail!");
        }
        return RestResult.success(majorList);
    }


    /**
     * 获取学校招生计划
     * @param map
     * @return
     */
    @RequestMapping("/getSchoolAdmission")
    public RestResult<List<SchoolAdmission>> getMSchoolMajorAdmission(@RequestBody Map<String,Object> map) {
        log.info("[arg]receive request: getSchoolAdmission");
        String province = "";
        String subject = "";
        String level = "";
        String name = "";
        if(map.get("name") != null){
            name = map.get("name").toString();
        }
        if(map.get("province") != null){
            province = map.get("province").toString();
        }
        if(map.get("subject") != null){
            subject = map.get("subject").toString();
        }
        if(map.get("level") != null){
            level = map.get("level").toString();
        }
        List<SchoolAdmission> schoolAdmissions = null;
        try {
            schoolAdmissions = schoolService.getSchoolAdmissionList(name,province,subject,level);
        }
        catch(Exception e){
            e.printStackTrace();
            return RestResult.error("get school admission list fail!");
        }
        return RestResult.success(schoolAdmissions);
    }

    /**
     * 获取专业招生计划
     * @param map
     * @return
     */
    @RequestMapping("/getMajorAdmission")
    public RestResult<List<MajorAdmission>> getMajorAdmission(@RequestBody Map<String,Object> map){
        log.info("[arg]receive request: getMajorAdmission");
        String province = "";
        String subject = "";
        String level = "";
        String name = "";
        if(map.get("name") != null){
            name = map.get("name").toString();
        }
        if(map.get("province") != null){
            province = map.get("province").toString();
        }
        if(map.get("subject") != null){
            subject = map.get("subject").toString();
        }
        if(map.get("year") != null){
            level = map.get("year").toString();
        }
        List<MajorAdmission> majorAdmissions = null;
        try{
            majorAdmissions = schoolService.getSchoolMajorAdmission(name,province,subject,level);
        }
        catch(Exception e){
            e.printStackTrace();
            return RestResult.error("get major admission list fail!");
        }
        return RestResult.success(majorAdmissions);
    }

}
