package com.example.demo.repository;

import com.example.demo.entity.neo4j.CountryMajor;
import com.example.demo.entity.neo4j.Major;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 11834
 */
@Repository
public interface MajorRepository extends Neo4jRepository<Major, Long> {
    /**
     * 专业大类获取专业小类  --本科
     * @param category
     * @return
     */
    @Query("MATCH (p:CountryMajorClass{country_major_class_name:$category})-[r:INCLUDE]->(IN) RETURN IN.country_major_smallclass_name as smallClass")
    List<String> getMajorCategoryByType(@Param("category") String category);

    /**
     * 专业大类获取专业小类  --专科学校本科专业
     * @param category
     * @return
     */
    @Query("MATCH (p:CountryMajorClassZhuanKe_BenKEZhuanYe{country_major_class_name:$category})-[r:INCLUDE]->(IN) RETURN IN.country_major_smallclass_name")
    List<String> getAssociateMajorCategoryByType(@Param("category") String category);

    /**
     * 专业大类获取专业小类  --专科学校专科专业
     * @param category
     * @return
     */
    @Query("MATCH (p:CountryMajorClassZhuanKe_ZhuanKEZhuanYe{country_major_class_name:$category})-[r:INCLUDE]->(IN) RETURN IN.country_major_smallclass_name")
    List<String> getAssociateZkMajorCategoryByType(@Param("category") String category);


    /**
     * 获取国家专业    --本科
     * @param category
     * @return
     */
    @Query("MATCH (p:CountryMajorSmallClass{country_major_smallclass_name:$category})-[r:INCLUDE]->(IN) RETURN IN")
    List<CountryMajor> getCountryMajorCategoryByType(@Param("category") String category);

    /**
     * 获取国家专业    --专科学校本科专业
     * @param category
     * @return
     */
    @Query("MATCH (p:CountryMajorSmallClassZhuanKe_BenKEZhuanYe{country_major_smallclass_name:$category})-[r:INCLUDE]->(IN) RETURN IN")
    List<CountryMajor> getAssociateCountryMajorCategoryByType(@Param("category") String category);

    /**
     * 获取国家专业    --专科学校专科专业
     * @param category
     * @return
     */
    @Query("MATCH (p:CountryMajorSmallClassZhuanKe_BenKEZhuanYe{country_major_smallclass_name:$category})-[r:INCLUDE]->(IN) RETURN IN")
    List<CountryMajor> getAssociateZkCountryMajorCategoryByType(@Param("category") String category);

    /**
     * 通过学校名称和专业名称获取志愿
     * @param schoolName
     * @param majorString
     * @param limit
     * @return
     */
    @Query("MATCH (s:School)-[r:`SCHOOL-MAJOR`]->(SM) WHERE s.school_name=$schoolName and SM.school_major =~ $majorString " +
            "return SM.school_major limit $limit")
    List<String> getMajorNameByClasses(@Param("schoolName") String schoolName, @Param("majorString")String majorString,@Param("limit") int limit);

    /**
     * 通过国家专业小类获取学校专业   ----本科
     * @param countryMajorSmallClasses
     * @return
     */
    @Query("MATCH (n:CountryMajorSmallClass)-[r:INCLUDE]->(CS)-[r1:INCLUDE]->(SM) WHERE " +
            "n.country_major_smallclass_name =~ $countryMajorSmallClasses RETURN SM.school_major")
    List<String> getSchoolMajorByCountrySmallMajorClass(@Param("countryMajorSmallClasses")String countryMajorSmallClasses);

    /**
     * 通过国家专业小类获取学校专业   ----专科学校本科专业
     * @param countryMajorSmallClasses
     * @return
     */
    @Query("MATCH (n:CountryMajorSmallClassZhuanKe_BenKEZhuanYe)-[r:INCLUDE]->(CS)-[r1:INCLUDE]->(SM) WHERE " +
            "n.country_major_smallclass_name =~ $countryMajorSmallClasses RETURN SM.school_major")
    List<String> getZKSchoolMajorByCountrySmallMajorClass(@Param("countryMajorSmallClasses")String countryMajorSmallClasses);

    /**
     * 通过国家专业小类获取学校专业   ----专科学校专科专业
     * @param countryMajorSmallClasses
     * @return
     */
    @Query("MATCH (n:CountryMajorSmallClassZhuanKe_ZhuanKEZhuanYe)-[r:INCLUDE]->(CS)-[r1:INCLUDE]->(SM) WHERE " +
            "n.country_major_smallclass_name =~ $countryMajorSmallClasses RETURN SM.school_major")
    List<String> getZK1SchoolMajorByCountrySmallMajorClass(@Param("countryMajorSmallClasses")String countryMajorSmallClasses);


}
