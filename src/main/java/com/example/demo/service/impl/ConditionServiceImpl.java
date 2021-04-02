package com.example.demo.service.impl;

import com.example.demo.dao.SearchConditionDao;
import com.example.demo.entity.SearchCondition;
import com.example.demo.service.ConditionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
选择条件实现类
 * @author yyh
 * @since 2021-03-08 23:42:45
 */
@Service("conditionService")
public class ConditionServiceImpl implements ConditionService {
    @Resource
    private SearchConditionDao searchConditionDao;
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SearchCondition selectById(Integer id){
        return this.searchConditionDao.selectById(id);
    }
    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limits  查询条数
     * @return 对象列表
     */
    @Override
    public List<SearchCondition> selectByAllConditions(int offset,int limits){
        return this.searchConditionDao.selectByAllConditions(offset, limits);
    }
    /**
     * 通过实体作为筛选条件查询
     * @param searchCondition
     * @return
     */
    @Override
    public List<SearchCondition> selectAll(SearchCondition searchCondition){
        return this.searchConditionDao.selectAll(searchCondition);
    }
}
