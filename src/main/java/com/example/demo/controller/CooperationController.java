package com.example.demo.controller;

import com.example.demo.entity.Cooperation;
import com.example.demo.model.RestResult;
import com.example.demo.model.UserInformation;
import com.example.demo.service.CooperationService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by 林夕
 * Date 2021/4/27 21:44
 */
@RequestMapping("/business")
@RestController
public class CooperationController {

    @Resource
    private CooperationService cooperationService;

    @PostMapping("/cooperation")
    public RestResult<Boolean> addCooperation(@RequestBody Cooperation cooperation,
                                              Authentication authentication){
        UserInformation u = (UserInformation) authentication.getPrincipal();
        return RestResult.success(cooperationService.add(cooperation,u));
    }

    @PostMapping("/modBusinessInfo")
    public RestResult<Boolean> modBusinessInfo(@RequestBody Cooperation cooperation){
        return RestResult.success(cooperationService.update(cooperation));
    }

    @GetMapping("/getBusinessInfo")
    public RestResult<Cooperation> getBusinessInfo(@RequestParam("openId") String openId){
        return RestResult.success(cooperationService.find(openId));
    }
}
