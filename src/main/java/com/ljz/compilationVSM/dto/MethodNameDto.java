package com.ljz.compilationVSM.dto;


import lombok.Data;

@Data
public class MethodNameDto {
    private String name;
    private String level;
    private Long commitNum;
    private String passPercent;
}
