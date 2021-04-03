package com.example.demo.dao;

import com.example.demo.entity.SearchCondition;

import java.util.List;
/**
*访问学校实体的相关数据
 * @author glitterglose
 * @since 2021-04-02 21:44
 */

public interface SearchConditionDao {
    /**
     通过ID查询单条数据
     *
     * @param id
     * @return 实例对象
     * */
    SearchCondition selectById(Integer id);
    /**
     *进行多条数据查询
     * @param offset 查询起始位置
     * @param limits 查询条数
     * @return 一个查询对象列表
     * * /
     */
    List<SearchCondition> selectByAllConditions(int offset,int limits);
    /**
     * 通过实体作为筛选条件查询
     * @param searchCondition
     * @return
     */
    List<SearchCondition> selectAll(SearchCondition searchCondition);
    /*
     *
     * */
}
