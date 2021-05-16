package com.example.demo.controller;

import com.example.demo.entity.Major;
import com.example.demo.model.RestResult;
import com.example.demo.service.MajorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 林夕
 * Date 2021/5/10 10:10
 */
@RequestMapping("/major")
@RestController

public class MajorController {

    @Resource
    private MajorService majorService;

    @GetMapping("/getMajorCategory")
    RestResult<Map<String,String>> getMajorCategory(@RequestParam String majorName){
        return RestResult.success(majorService.getMajorCategory(majorName));
    }

    @GetMapping("/getMajorCard")
    RestResult<Major> getMajorCard(@RequestParam String name){
        return RestResult.success(majorService.getMajorCard(name));
    }
}
