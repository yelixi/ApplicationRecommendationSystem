package com.example.demo.service.impl;

import com.example.demo.dao.SearchConditionDao;
import com.example.demo.entity.SchoolResult;
import com.example.demo.entity.SearchCondition;
import com.example.demo.model.MajorConditions;
import com.example.demo.model.MajorInformation;
import com.example.demo.model.SocketConstant;
import com.example.demo.service.ConditionService;
import com.example.demo.util.SocketClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
     * @param  name 学校名称
     * @return 实例对象
     */
    @Override
    public SchoolResult selectByName(String name){
        return this.searchConditionDao.selectByName(name);
    }
    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limits  查询条数
     * @return 对象列表
     */
    @Override
    public List<SchoolResult> selectByAllConditions(int offset, int limits){
        return this.searchConditionDao.selectByAllConditions(offset, limits);
    }
    /**
     * 通过实体作为筛选条件查询
     * @param searchCondition
     * @return
     */
    @Override
    public List<SchoolResult> selectAll(SearchCondition searchCondition){
        return this.searchConditionDao.selectAll(searchCondition);
    }

    @Override
    public List<MajorInformation> selectMajor(MajorConditions majorConditions) {
       Object obj = SocketClient.socketHandle(SocketConstant.SELECT_MAJOR,majorConditions);
       List<MajorInformation> list = new ArrayList<>();
        if (obj instanceof ArrayList<?>) {
            for (Object o : (List<?>) obj) {
                list.add((MajorInformation) o);
            }
        }
        return list;
    }
}
