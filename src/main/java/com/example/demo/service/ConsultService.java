package com.example.demo.service;

import com.example.demo.entity.Consult;
import com.github.pagehelper.PageInfo;

/**
 * Created by 林夕
 * Date 2021/5/16 15:49
 */
public interface ConsultService {
    PageInfo<Consult> List();

    boolean addReader(Integer consultId);

    boolean addThumpUp(Integer consultId);
}
