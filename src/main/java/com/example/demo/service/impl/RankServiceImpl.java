package com.example.demo.service.impl;

import com.example.demo.entity.CommendCondition;
import com.example.demo.entity.Ranking;
import com.example.demo.entity.SchoolResult;
import com.example.demo.model.SocketConstant;
import com.example.demo.service.RankService;
import com.example.demo.util.SocketClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class RankServiceImpl implements RankService {

    @Override
    public List<Ranking> selectRank(CommendCondition condition) throws JsonProcessingException {
        List<Ranking> list = new ArrayList<>();
        try{
            Object obj = SocketClient.socketHandle(SocketConstant.SELECT_MAJOR, new ObjectMapper().writeValueAsString(condition));
            if (obj instanceof ArrayList<?>) {
                for (Object o : (List<?>) obj) {
                    list.add((Ranking) o);
                }
            }
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Ranking> sort(CommendCondition condition){
        List<Ranking> list = new ArrayList<>();

        return list;
    }
}
