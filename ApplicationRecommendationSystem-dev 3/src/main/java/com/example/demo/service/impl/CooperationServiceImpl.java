package com.example.demo.service.impl;

import com.example.demo.dao.CooperationMapper;
import com.example.demo.entity.Cooperation;
import com.example.demo.model.UserInformation;
import com.example.demo.service.CooperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by 林夕
 * Date 2021/4/27 21:45
 */
@Service
@Slf4j
public class CooperationServiceImpl implements CooperationService {

    @Resource
    public CooperationMapper cooperationMapper;

    @Override
    public boolean add(Cooperation cooperation, UserInformation u) {
        cooperation.setUserId(u.getId());
        cooperation.setUpdateTime(new Date());
        return cooperationMapper.insert(cooperation) == 1;
    }

    @Override
    public boolean update(Cooperation cooperation) {
        int id = cooperationMapper.selectByOpenIdAndComName(cooperation.getOpenId()
                ,cooperation.getComName()).getId();
        cooperation.setId(id);
        cooperation.setUpdateTime(new Date());
        return cooperationMapper.updateByPrimaryKeySelective(cooperation)==1;
    }

    @Override
    public Cooperation find(String openId) {
        return cooperationMapper.selectByOpenId(openId);
    }
}
