package com.example.demo.controller;

import com.example.demo.entity.neo4j.CountryMajor;
import com.example.demo.entity.neo4j.Major;
import com.example.demo.model.RestResult;
import com.example.demo.service.MajorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by 林夕
 * Date 2021/5/10 10:10
 * @author 11834
 */
@Slf4j
@RequestMapping("/major")
@RestController
public class MajorController {

    @Resource
    private MajorService majorService;

    /**
     * 获取专业小类
     * @param map
     * @return
     */
    @PostMapping("/getMajorCategory")
    RestResult<List<String>> getMajorCategory(@RequestBody Map<String,Object> map){
        log.info("[arg]receive request: getMajorCategory");
        String category = "";
        //true:本科，false:专科
        Boolean undergraduate = true;
        if(map.get("category") != null){
            category = map.get("category").toString();
        }
        if(map.get("undergraduate") != null){
            String undergraduateString = map.get("undergraduate").toString();
            undergraduate = Boolean.parseBoolean(undergraduateString);
        }
        List<String> categoryList = null;
        try{
            categoryList = majorService.getMajorCategory(category,undergraduate);
        }
        catch(Exception e){
            e.printStackTrace();
            RestResult.error("get major category fail!");
        }
        return RestResult.success(categoryList);
    }

    /**
     * 获取专业
     * @param map
     * @return
     */
    @PostMapping("/getMajorSmallCategory")
    RestResult<List<CountryMajor>> getMajorSmallCategory(@RequestBody Map<String,Object> map){
        log.info("[arg]receive request: getMajorSmallCategory");
        String category = "";
        //true:本科，false:专科
        boolean undergraduate = true;
        if(map.get("category") != null){
            category = map.get("category").toString();
        }
        if(map.get("undergraduate") != null){
            undergraduate = Boolean.parseBoolean(map.get("undergraduate").toString());
        }
        List<CountryMajor> categoryList = null;
        try{
            categoryList = majorService.getMajorSmallCategory(category,undergraduate);
        }
        catch(Exception e){
            e.printStackTrace();
            RestResult.error("get major category fail!");
        }
        return RestResult.success(categoryList);
    }


    @GetMapping("/getMajorCard")
    RestResult<Major> getMajorCard(@RequestParam String name){
        return RestResult.success(majorService.getMajorCard(name));
    }
}
