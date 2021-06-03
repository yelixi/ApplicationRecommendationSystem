package com.example.demo.service;

import com.example.demo.entity.Cooperation;
import com.example.demo.model.UserInformation;

/**
 * Created by 林夕
 * Date 2021/4/27 21:44
 */
public interface CooperationService {

    boolean add(Cooperation cooperation, UserInformation u);

    boolean update(Cooperation cooperation);

    Cooperation find(String openId);
}
