package com.example.demo.controller;

import com.example.demo.model.RestResult;
import com.example.demo.service.HomeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by 林夕
 * Date 2021/5/20 0:35
 */
@RequestMapping("/home")
@RestController
public class HomeController {

    @Resource
    private HomeService homeService;

    @GetMapping("/find")
    RestResult<Object> find(@RequestParam("type") String type,
                            @RequestParam("search") String search){
        return RestResult.success(homeService.find(type,search));
    }
}
