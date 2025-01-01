package com.ljz.compilationVSM.domain.ObjQuestion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CheckedChooseDTO {
    private List<CheckUnitDTO> checkUnitList;
    @Data
    @AllArgsConstructor
    public static class CheckUnit{
        String answer;
        Boolean mark;
    }
}
