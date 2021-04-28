package com.example.demo.service.impl;

import com.example.demo.dao.FeedbackMapper;
import com.example.demo.entity.Feedback;
import com.example.demo.model.UserInformation;
import com.example.demo.service.FeedbackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by 林夕
 * Date 2021/4/27 23:12
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Resource
    private FeedbackMapper feedbackMapper;

    @Override
    public boolean add(Feedback feedback, UserInformation u) {
        feedback.setId(u.getId());
        feedback.setUpdateTime(new Date());
        return feedbackMapper.insert(feedback)==1;
    }
}
