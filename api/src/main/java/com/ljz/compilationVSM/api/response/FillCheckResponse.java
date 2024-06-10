package com.ljz.compilationVSM.api.response;

import lombok.Data;

import java.util.List;

@Data
public class ChooseCheckResponse {
    private List<CheckUnit> checkUnitList;
    @Data
    public static class CheckUnit{
        Integer id;
        String answer;
        Boolean mark;
    }
}
