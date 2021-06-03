package com.example.demo.controller;

import com.example.demo.entity.neo4j.ProvinceCity;
import com.example.demo.entity.neo4j.SchoolInfo;
import com.example.demo.model.RestResult;
import com.example.demo.service.AreaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 11834
 */
@Slf4j
@RestController
@RequestMapping("/area")
public class AreaController {
    @Autowired
    private AreaService areaService;


    @RequestMapping("/getCity")
    public RestResult<List<ProvinceCity>> getCity() {
        log.info("[arg]receive request: getCity");
        List<ProvinceCity> provinceCities= null;
        try {
            provinceCities = areaService.getCities();
        }
        catch(Exception e){
            e.printStackTrace();
            return RestResult.error("get getCity info fail");
        }
        return RestResult.success(provinceCities);
    }
}
