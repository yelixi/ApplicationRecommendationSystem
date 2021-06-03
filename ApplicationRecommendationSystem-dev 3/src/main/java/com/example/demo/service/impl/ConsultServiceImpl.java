package com.example.demo.service.impl;

import com.example.demo.dao.ConsultMapper;
import com.example.demo.entity.Consult;
import com.example.demo.service.ConsultService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 林夕
 * Date 2021/5/16 15:49
 */
@Service
public class ConsultServiceImpl implements ConsultService {

    @Resource
    private ConsultMapper consultMapper;

    @Override
    public PageInfo<Consult> List() {
        int pageNum=5,pageSize;
        List<Consult> list = consultMapper.findAll();
        pageSize=list.size()/pageNum;
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Consult> page = new PageInfo<>();
        page.setList(list);
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        return page;
    }

    @Override
    public boolean addReader(Integer consultId) {
        Consult consult = consultMapper.selectByPrimaryKey(consultId);
        consult.setReadTimes(consult.getReadTimes()+1);
        return consultMapper.updateByPrimaryKeySelective(consult)==1;
    }

    @Override
    public boolean addThumpUp(Integer consultId) {
        Consult consult = consultMapper.selectByPrimaryKey(consultId);
        consult.setThumbUpTimes(consult.getThumbUpTimes()+1);
        return consultMapper.updateByPrimaryKeySelective(consult)==1;
    }
}
