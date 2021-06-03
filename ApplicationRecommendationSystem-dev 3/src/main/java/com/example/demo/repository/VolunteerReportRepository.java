package com.example.demo.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 11834
 */
@Repository
public interface VolunteerReportRepository extends Neo4jRepository<String, Long> {

    /**
     * 根据分数获取排名
     *
     * @param province
     * @param totalScore
     * @param subject
     * @param year
     * @return
     */
    @Query("MATCH (n:Admission_Information) WHERE n.admission_year = $year AND n.admission_province=$province AND n.subject = $subject AND n.minimum_score=$totalScore RETURN n.minimum_rank as rank limit 1")
    int getRankByScore(@Param("province")String province, @Param("totalScore")String totalScore, @Param("subject")String subject,@Param("year")String year);



}
