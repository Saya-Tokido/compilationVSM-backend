package com.ljz.compilationVSM.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MethodBodyDto {
    Integer id;
    String description;
    String input;
    String output;
    String inParam;
    String outParam;
    String body;
}
