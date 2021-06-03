package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.repository.AdmissionRepository;
import com.example.demo.repository.MajorRepository;
import com.example.demo.repository.SchoolRepository;
import com.example.demo.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private AdmissionRepository admissionRepository;
    @Autowired
    private MajorRepository majorRepository;


    @Override
    public School findSchoolBrief(String name) {
        return schoolRepository.findSchool(name);
    }

    @Override
    public List<SchoolAdmission> getSchoolAdmissionBySchoolName(String name) {
        return admissionRepository.getSchoolAdmission(name);
    }

    @Override
    public List<MajorAdmission> getMajorBySchoolName(String name) {
        return admissionRepository.getMajorAdmission(name);
    }

    @Override
    public List<String> getMajorListBySchoolName(String name) {
        return majorRepository.getMajorList(name);
    }

    @Override
    public MajorInfo getMajorInfo(String schoolName, String majorName) {
        return majorRepository.getMajorInfo(schoolName,majorName);
    }

    @Override
    public int getRankByScore(String province, float totalScore, String subject, int year) {
        return schoolRepository.getRank(province, totalScore, subject, year);
    }

    @Override
    public List<SchoolInfo> getSchoolInfoListReport(int maxRank, int minRank, String subject, String mode) {
        List<SchoolInfo> schoolInfoList = null;
        if(subject.isEmpty()){
            schoolInfoList = schoolRepository.getSchoolInfoList(maxRank, minRank);
        }
        else{
            schoolInfoList = schoolRepository.getSchoolInfoListBySubject(maxRank, minRank, subject);
        }
        return schoolInfoList;
    }

    @Override
    public Integer getRankLastYear(String subject, int year,float score) {
        return schoolRepository.getRankBySubject(subject, year, score);
    }

    @Override
    public List<String> getMajorByCodes(String schoolName, List<String> codes, int limit) {
        return majorRepository.getMajorNameList(schoolName,codes,limit);
    }

    @Override
    public List<String> getMajorByCode(String schoolName, String code, int limit) {
        return majorRepository.getMajorName(schoolName,code,limit);
    }

    @Override
    public SchoolInfo findSchoolInfo(String name) {
        return schoolRepository.getSchoolInfo(name);
    }
}
