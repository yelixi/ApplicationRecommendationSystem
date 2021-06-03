package com.example.demo.service.impl;

import com.example.demo.entity.CommendCondition;
import com.example.demo.entity.SchoolResult;
import com.example.demo.entity.SearchCondition;
import com.example.demo.model.SocketConstant;
import com.example.demo.service.CommendService;
import com.example.demo.util.SocketClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("commendService")
public class CommendServiceImpl implements CommendService {

    @Override
    public List<SchoolResult> selectByCondition(CommendCondition condition) throws JsonProcessingException {
        List<SchoolResult> list = new ArrayList<>();
        try {
            Object obj = SocketClient.socketHandle(SocketConstant.SELECT_MAJOR, new ObjectMapper().writeValueAsString(condition));
            if (obj instanceof ArrayList<?>) {
                for (Object o : (List<?>) obj) {
                    list.add((SchoolResult) o);
                }
            }
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return list;

    }

}
