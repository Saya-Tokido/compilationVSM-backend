package com.ljz.compilationVSM.domain.ObjQuestion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CheckedChooseDTO {
    private List<CheckUnit> checkUnitList;
    @Data
    @AllArgsConstructor
    public static class CheckUnit{
        String id;
        String answer;
        Boolean mark;
    }
}
