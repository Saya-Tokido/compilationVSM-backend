package com.ljz.compilationVSM.api.request.student;

import lombok.Data;

import java.util.List;

@Data
public class FillCheckRequest {
    private List<Answer> answers;

    @Data
    public static class Answer {

        private String id;

        private String answer;
    }
}
