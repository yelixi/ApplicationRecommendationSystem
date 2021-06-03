package com.example.demo.service.impl;

import com.example.demo.entity.neo4j.CountryMajor;
import com.example.demo.entity.neo4j.Major;
//import com.example.demo.repository.MajorRepository;
import com.example.demo.repository.MajorRepository;
import com.example.demo.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 林夕
 * Date 2021/5/10 10:14
 * @author 11834
 */
@Service
public class MajorServiceImpl implements MajorService {

    @Autowired
    private MajorRepository majorRepository;

    @Override
    public List<String> getMajorCategory(String category,boolean undergraduate) {
        List<String> majorClasses = null;
        if(undergraduate){
            majorClasses = majorRepository.getMajorCategoryByType(category);
        }
        else{
            majorClasses = majorRepository.getAssociateMajorCategoryByType(category);
            majorClasses.addAll(majorRepository.getAssociateZkMajorCategoryByType(category));
        }
        return majorClasses;
    }

    @Override
    public List<CountryMajor> getMajorSmallCategory(String smallCategory, boolean undergraduate) {
        List<CountryMajor> countryMajors = new ArrayList<>();
        if(undergraduate){
            countryMajors = majorRepository.getCountryMajorCategoryByType(smallCategory);
        }
        else{
            countryMajors = majorRepository.getAssociateCountryMajorCategoryByType(smallCategory);
            countryMajors.addAll(majorRepository.getAssociateZkCountryMajorCategoryByType(smallCategory));
        }
        return countryMajors;
    }

    @Override
    public Major getMajorCard(String name) {
        //TODO
        return null;
    }

    @Override
    public List<String> getSchoolMajorsBySmallClasses(List<String> majorNames,boolean undergraduate) {
        List<String> schoolMajors = null;
        String majors = "";
        for(int i=0;i<majorNames.size();i++){
            if(i != 0){
                majors = majors + "|";
            }
            majors = majors + ".*" + majorNames.get(i) + ".*";
        }
        if(undergraduate) {
            schoolMajors = majorRepository.getSchoolMajorByCountrySmallMajorClass(majors);
        }
        else
        {
            schoolMajors = majorRepository.getZKSchoolMajorByCountrySmallMajorClass(majors);
            schoolMajors.addAll(majorRepository.getZK1SchoolMajorByCountrySmallMajorClass(majors));
        }
        return schoolMajors;
    }

    @Override
    public List<String> getMajorByMajorClasses(String schoolName, List<String> majorNames, int limit) {
        String majors = "";
        for(int i=0;i<majorNames.size();i++){
            if(i != 0){
                majors = majors + "|";
            }
            majors = majors + ".*" + majorNames.get(i) + ".*";
        }
        System.out.println("======majors====="+majors);
        return majorRepository.getMajorNameByClasses(schoolName,majors,limit);
    }

    @Override
    public List<String> getMajorByMajorClass(String schoolName, String majorName, int limit) {

        return null;
    }
}
