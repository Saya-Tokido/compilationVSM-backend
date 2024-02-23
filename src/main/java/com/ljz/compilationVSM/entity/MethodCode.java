package com.ljz.compilationVSM.entity;

import lombok.Data;

@Data
public class MethodCode {
    private Long id;
    private String content;
    private Integer sourceId;
    private String method;
    private String testVar;
}
