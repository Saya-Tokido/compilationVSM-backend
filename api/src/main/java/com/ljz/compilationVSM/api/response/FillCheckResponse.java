package com.ljz.compilationVSM.api.response;

import lombok.Data;

import java.util.List;

@Data
public class FillCheckResponse {
    private List<CheckUnit> checkUnitList;
    @Data
    public static class CheckUnit{
        String id;
        String answer;
        Boolean mark;
    }
}
