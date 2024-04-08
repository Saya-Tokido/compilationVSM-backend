package com.ljz.compilationVSM.dto;


import lombok.Data;

@Data
public class MethodNameDto {
    Integer id;
    String name;
    String level;
    Long commitNum;
    String passPercent;
}
