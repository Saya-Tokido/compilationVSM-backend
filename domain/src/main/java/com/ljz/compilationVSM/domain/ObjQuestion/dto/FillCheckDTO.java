package com.ljz.compilationVSM.domain.ObjQuestion.dto;

import lombok.Data;

import java.util.List;

@Data
public class FillCheckDTO {
    private List<Answer> answers;

    @Data
    public static class Answer {

        private String id;

        private String answer;
    }
}
