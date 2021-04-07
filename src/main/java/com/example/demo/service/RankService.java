package com.example.demo.service;

import com.example.demo.entity.CommendCondition;
import com.example.demo.entity.Ranking;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface RankService {

    List<Ranking> selectRank(CommendCondition condition) throws JsonProcessingException;
}
