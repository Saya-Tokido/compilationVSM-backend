package com.ljz.compilationVSM.api.request.student;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class ChooseCheckRequest {
    private List<Answer> answers;

    @Data
    public static class Answer {

        private Long id;

        private String answer;
    }
}
