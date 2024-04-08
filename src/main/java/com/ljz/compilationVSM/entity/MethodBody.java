package com.ljz.compilationVSM.entity;

import lombok.Data;

@Data
public class MethodBody {
    private Integer id;
    private Integer methodId;
    private String description;
    private String input;
    private String output;
    private String inParam;
    private String outParam;
    private String body;
    private String checkBody;
}
