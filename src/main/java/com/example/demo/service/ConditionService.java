package com.example.demo.service;

import com.example.demo.entity.SchoolResult;
import com.example.demo.entity.SearchCondition;
import com.example.demo.model.MajorConditions;
import com.example.demo.model.MajorInformation;

import java.util.List;

public interface ConditionService {
    /**
      通过name查询单条数据
     *
     * @param name
     * @return 实例对象
     * */
    SchoolResult selectByName(String name);
    /**
     *进行多条数据查询
     * @param offset 查询起始位置
     * @param limits 查询条数
     * @return 一个查询对象列表
     * * /
     */
    List<SchoolResult> selectByAllConditions(int offset, int limits);
    /**
    * 通过实体作为筛选条件查询
     * @param searchCondition
     * @return
     */
    List<SchoolResult> selectAll(SearchCondition searchCondition);


    /**
     * 查询专业
     */

    List<MajorInformation> selectMajor(MajorConditions majorConditions);
}
