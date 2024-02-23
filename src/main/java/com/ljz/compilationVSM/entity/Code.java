package com.ljz.compilationVSM.entity;

import lombok.Data;

@Data
public class Code {
    private Long id;
    private String content;
    private Integer sourceId;
    private String method;
}
