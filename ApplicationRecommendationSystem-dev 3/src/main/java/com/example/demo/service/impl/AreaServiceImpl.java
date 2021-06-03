package com.example.demo.service.impl;

import com.example.demo.entity.neo4j.ProvinceCity;
import com.example.demo.repository.AreaRepository;
import com.example.demo.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 11834
 */
@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaRepository areaRepository;

    @Override
    public List<ProvinceCity> getCities() {
        List<ProvinceCity> provinceCities = new ArrayList<>();
        List<String> provinces = areaRepository.getProvinces();
        if(provinces != null && provinces.size() > 0){
            for(String province:provinces){
                ProvinceCity provinceCity = new ProvinceCity();
                List<String> cities = areaRepository.getCitiesByProvince(province);
                provinceCity.setProvince(province);
                provinceCity.setCity(cities);
                provinceCities.add(provinceCity);
            }
        }
        return provinceCities;
    }
}
