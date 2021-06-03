package com.example.demo.service.impl;

import com.example.demo.repository.VolunteerReportRepository;
import com.example.demo.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 11834
 */
@Service
public class VolunteerServiceImpl implements VolunteerService {
    @Autowired
    private VolunteerReportRepository volunteerReportRepository;

    @Override
    public int getRankByScore(String province, int totalScore, String subject, int year) {
        int rank = 0;
        try{
            rank = volunteerReportRepository.getRankByScore(province,String.valueOf(totalScore), subject, String.valueOf(year));
        }
        catch(Exception e){
            totalScore = totalScore - 1;
            rank = getRankByScore(province, totalScore, subject, year);
        }
        return rank;
    }

}
