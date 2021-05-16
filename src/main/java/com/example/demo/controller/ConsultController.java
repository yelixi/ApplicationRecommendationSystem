package com.example.demo.controller;

import com.example.demo.entity.Consult;
import com.example.demo.model.RestResult;
import com.example.demo.service.ConsultService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Console;
import java.util.List;

/**
 * Created by 林夕
 * Date 2021/5/16 14:47
 */
@RestController
@RequestMapping("/consult")
public class ConsultController {

    @Resource
    private ConsultService consultService;

    @GetMapping("/getConsoleList")
    RestResult<PageInfo<Consult>> getConsoleList(){
        return RestResult.success(consultService.List());
    }

    @PostMapping("/addReader")
    RestResult<Boolean> addReader(@RequestParam("id") Integer consultId){
        return RestResult.success(consultService.addReader(consultId));
    }

    @PostMapping("/addThumpUp")
    RestResult<Boolean> addThumpUp(@RequestParam("id") Integer consultId){
        return RestResult.success(consultService.addThumpUp(consultId));
    }
}
