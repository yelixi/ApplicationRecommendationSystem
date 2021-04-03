package com.example.demo.model;

import com.example.demo.entity.SearchCondition;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SelectCondition extends SearchCondition {

}