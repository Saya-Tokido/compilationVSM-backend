package com.ljz.compilationVSM.domain.ObjQuestion.dto;

import lombok.Data;

import java.util.List;

@Data
public class CheckedChooseDTO {
    private List<CheckUnit> checkUnitList;
    @Data
    public static class CheckUnit{
        Integer id;
        String answer;
        Boolean mark;
    }
}
