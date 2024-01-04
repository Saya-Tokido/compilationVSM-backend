package com.ljz.compilationVSM.entity;

import lombok.Data;

import java.util.List;

@Data
public class ChooseBody {
    Integer id;
    String question;
    List<String> choiceList;
}
