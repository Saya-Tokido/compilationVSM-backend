package com.ljz.compilationVSM.entity;

import lombok.Data;

@Data
public class Grade {
    Long id;
    Short score;
    Integer userId;
    Short experimentId;
    String difficulty;
}
