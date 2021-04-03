package com.example.demo.controller;

import com.example.demo.entity.CommendCondition;
import com.example.demo.model.RestResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("commend")
public class CommendController {

    @PostMapping("commendCondition")
    public RestResult<Object> commendByCondition(@RequestBody CommendCondition condition){
        if (condition == null)
        {
            return RestResult.error("条件为空");
        }
        return  null;
    }
}
