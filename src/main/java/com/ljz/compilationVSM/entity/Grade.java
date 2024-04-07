package com.ljz.compilationVSM.entity;

import lombok.Data;

@Data
public class Grade {
    private Long id;
    private Short score;
    private Integer userId;
    private Short experimentId;
    private String difficulty;
}
