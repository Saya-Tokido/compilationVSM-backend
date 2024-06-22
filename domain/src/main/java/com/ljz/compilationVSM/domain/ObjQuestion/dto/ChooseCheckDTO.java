package com.ljz.compilationVSM.domain.ObjQuestion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChooseCheckDTO {
    private List<Answer> answers;

    @Data
    public static class Answer {

        private String id;

        private String answer;
    }
}
