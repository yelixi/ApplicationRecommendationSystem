package com.example.demo.controller;

import com.example.demo.entity.SchoolResult;
import com.example.demo.entity.SearchCondition;
import com.example.demo.model.MajorConditions;
import com.example.demo.model.MajorInformation;
import com.example.demo.model.RestResult;
import com.example.demo.service.ConditionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/search")
public class SearchController {


    @Resource
    private ConditionService conditionService;

    /**
     *
     * @param condition 查询条件
     * @return 查询结果
     */
    @PostMapping("/selectAll")
    public RestResult<List<SchoolResult>> selectAll(@RequestBody SearchCondition condition){
        if (condition==null){
            return RestResult.error("条件为空");
        }

        return  RestResult.success(this.conditionService.selectAll(condition));
    }


    @PostMapping("selectOne")
    public RestResult<SchoolResult> selectOne(@RequestParam("schoolname") String name){
        if (name == null){
            return RestResult.error("名称为空");
        }
        if (conditionService.selectByName(name) == null){
            return RestResult.error("未查找到该学校");
        }else {
            return RestResult.success(conditionService.selectByName(name));
        }
    }


    /**
     * 查询专业
     */
    @GetMapping("/selectMajor")
    public RestResult<List<MajorInformation>> selectMajor(@RequestBody MajorConditions majorConditions){
        return RestResult.success(this.conditionService.selectMajor(majorConditions));
    }
}
