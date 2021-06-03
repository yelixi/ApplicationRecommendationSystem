package com.example.demo.service;

import com.example.demo.entity.neo4j.ProvinceCity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 11834
 */
public interface AreaService {

    /**
     * 根据省份获取城市
     * @return
     */
    List<ProvinceCity> getCities();
}
