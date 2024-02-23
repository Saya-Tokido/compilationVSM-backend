package com.ljz.compilationVSM.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChooseDto {
    Long id;
    String question;
    List<String> choiceList;
    Boolean mark;
}
