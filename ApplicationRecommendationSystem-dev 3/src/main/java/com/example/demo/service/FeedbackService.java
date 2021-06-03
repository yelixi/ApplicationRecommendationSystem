package com.example.demo.service;

import com.example.demo.entity.Feedback;
import com.example.demo.model.UserInformation;

/**
 * Created by 林夕
 * Date 2021/4/27 23:12
 */
public interface FeedbackService {
    boolean add(Feedback feedback, UserInformation u);
}
