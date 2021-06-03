package com.example.demo.service;

/**
 * @author 11834
 */
public interface VolunteerService {

    /**
     *
     * 根据总分获取所在省份的排名
     *
     * @param province
     * @param totalScore
     * @param subject
     * @param year
     * @return
     */
    int getRankByScore(String province, int totalScore,String subject, int year);

}
