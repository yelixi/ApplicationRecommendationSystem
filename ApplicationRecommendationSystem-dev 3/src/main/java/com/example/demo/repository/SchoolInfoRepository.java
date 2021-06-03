package com.example.demo.repository;

import com.example.demo.entity.neo4j.*;
import org.neo4j.ogm.model.Result;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author 11834
 */
@Repository
public interface SchoolInfoRepository extends Neo4jRepository<SchoolInfo, Long> {
    /**
     * 获取学校信息
     * @param name
     * @return
     */
    @Query("MATCH (p:School{school_name:$name})-[:EDUCATION_WAY]->(EW)," +
            "(p:School{school_name:$name})-[:SCHOOL_LABEL]->(SL)," +
            "(p:School{school_name:$name})-[:SCHOOL_CITY]->(SC)," +
            "(p:School{school_name:$name})-[:SCHOOL_PROVINCE]->(SP)," +
            "(p:School{school_name:$name})-[:EDUCATION_TYPE]->(ET)," +
            "(p:School{school_name:$name})-[r:SCHOOL_PICTURE]->(SM)," +
            "(p:School{school_name:$name})-[:COLLEGE_OWNER]->(CO) " +
            " RETURN p as school,EW.education_way_name as educationWay,SL as schoolLabel,SC.city_name as city," +
            "SP.province_name as province,ET.education_type_name as educationType, SM as pictures, " +
            "CO.college_owner_name as collegeOwner")
    Result getSchoolInfo(@Param("name") String name);

    /**
     * 学校名称获取专业列表
     * @param name
     * @return
     */
    @Query("MATCH (p:School{school_name:$name})-[r:`SCHOOL-MAJOR`]->(SM) RETURN SM")
    List<Major> getMajorListBySchoolName(@Param("name") String name);

    /**
     * 获取当年最低分数
     * @param name
     * @param province
     * @param level
     * @param subject
     * @param year
     * @return
     */
    @Query("MATCH (p:School{school_name:$name})-[r:SCHOOL_ADMISSIONINFORMATION]->(MA) WHERE MA.admission_province =$province AND MA.admission_year = $year AND MA.subject=$subject AND MA.minimum_score > '0' RETURN min(MA.minimum_score)")
    String getMinAdmissionScore(@Param("name") String name, @Param("province") String province, @Param("level") String level, @Param("subject") String subject,@Param("year") String year);

    /**
     * 根据最低分，获取学校招生计划
     * @param name
     * @param province
     * @param level
     * @param subject
     * @param year
     * @param minScore
     * @return
     */
    @Query("MATCH (p:School{school_name:$name})-[r:SCHOOL_ADMISSIONINFORMATION]->(MA) WHERE MA.admission_province =$province AND MA.admission_year = $year AND MA.subject=$subject AND MA.minimum_score = $minScore RETURN MA limit 1")
    SchoolAdmission getAdmissionByMinScore(@Param("name") String name, @Param("province") String province, @Param("level") String level, @Param("subject") String subject,@Param("year") String year,@Param("minScore")String minScore);

    /**
     * 获取专业招生计划
     * @param name
     * @param province
     * @param year
     * @param subject
     * @return
     */
    @Query("MATCH (p:School{school_name:$name})-[r:`SCHOOL-MAJOR`]->(SM)-[r2:MAJOR_ADMISSIONINFORMATION]->(A) " +
            "WHERE A.admission_province = $province AND A.subject=$subject AND " +
            "A.admission_year = $year RETURN SM.id as id,SM.school_major as majorName,A.minimum_score as minScore," +
            "A.minimum_rank as minRank,A.average_score as aveScore,A.admission_layer as layer limit 20")
    Result getMajoAdmission(String name,String province,String year,String subject);


    /**
     * 根据分数排名获取对应学校名称
     * @param maxRank
     * @param minRank
     * @param subject
     * @param mode
     * @param province
     * @param year
     * @return
     */
    @Query("MATCH (n:School)-[r1:SCHOOL_ADMISSIONINFORMATION]->(SA) where toInteger(SA.minimum_rank) > $minRank AND toInteger(SA.minimum_rank) < $maxRank " +
            "AND SA.subject = $subject  AND SA.admission_layer= $mode AND SA.admission_province = $province " +
            "AND SA.admission_year = $year RETURN distinct n.school_name as name,SA.minimum_rank as minRank," +
            "SA.minimum_score as minScore order by minRank")
    Result getSchoolByScoreRank(@Param("year")String year,@Param("province")String province,@Param("maxRank")int maxRank,@Param("minRank") int minRank, @Param("subject")String subject,@Param("mode")String mode);

    /**
     * 根据分数排名获取对应学校名称
     * @param maxRank
     * @param minRank
     * @param mode
     * @param province
     * @param year
     * @return
     */
    @Query("MATCH(p:School)-[r:SCHOOL_ADMISSIONINFORMATION]->(A:Admission_Information) " +
            "WHERE toInteger(A.minimum_rank) > $minRank AND toInteger(A.minimum_rank) < $maxRank " +
            "AND A.admission_layer= $mode AND A.admission_province = $province " +
            "AND A.admission_year = $year RETURN distinct p.school_name,SA.minimum_rank as minRank, " +
            "SA.minimum_score as minScore order by minRank")
    Result getSchoolByScoreRankNoSubject(@Param("year")String year,@Param("province")String province,@Param("maxRank")int maxRank,@Param("minRank") int minRank,@Param("mode")String mode);

    /**
     * 查询重点实验室
     * @param name
     * @return
     */
    @Query("MATCH (p:School{school_name:$name})-[r:LABORATORY]->(MA)  RETURN MA.laboratory_name")
    List<String> getSchoolLaboratoryByName(String name);


    /**
     * 查询博士点硕士点
     * @param name
     * @return
     */
    @Query("MATCH (p:School{school_name:$name})-[r:DOCTOR_MASTER_STATION]->(DM)  " +
            "RETURN DM.master_station_amount as masterPoint,DM.doctor_station_amount as doctorPoint")
    Result getSchoolDoctorMasterPointByName(String name);


    /**
     * 查询招生简章
     * @param name
     * @return
     */
    @Query("MATCH (p:School{school_name:$name})-[r:SCHOOL_ZHAOSHENGJIANZHANG]->(SZ)  " +
            "RETURN SZ.jianzhang_url as admissionUrl")
    List<String> getSchoolAdmissionUrlByName(String name);



    /**
     * 查询学校简介
     * @param name
     * @return
     */
    @Query("MATCH (p:School{school_name:$name})-[r:SCHOOL_INTRODUCTION]->(SI) " +
            "RETURN SI.neirong as introduction")
    String getSchoolIntroductionByName(String name);

}
