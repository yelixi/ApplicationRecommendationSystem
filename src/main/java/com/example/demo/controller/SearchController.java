package com.example.demo.controller;

import com.example.demo.entity.SearchCondition;
import com.example.demo.model.MajorConditions;
import com.example.demo.model.MajorInformation;
import com.example.demo.model.RestResult;
import com.example.demo.service.ConditionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
*搜索志愿控制层
 *
 * @author glitterglose
 * @since 2021-04-02 22:00
 */
@RestController
@RequestMapping("/searchCondition")
public class SearchController {
    /**
    *搜索对象
     */
    @Resource
    private ConditionService conditionService;
    /**
     * 通过主键查询id
     */
    @GetMapping("/selectOne")
    public SearchCondition selectOne(Integer id){
        return this.conditionService.selectById(id);
    }
    /**
     *
     */

    /**
     * 查询专业
     */
    @GetMapping("/selectMajor")
    public RestResult<List<MajorInformation>> selectMajor(@RequestBody MajorConditions majorConditions){
        return RestResult.success(this.conditionService.selectMajor(majorConditions));
    }
}
