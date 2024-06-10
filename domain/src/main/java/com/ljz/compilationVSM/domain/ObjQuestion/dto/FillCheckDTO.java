package com.ljz.compilationVSM.api.request;

import lombok.Data;

import java.util.List;

@Data
public class FillCheckRequest {
    private List<Answer> answers;

    @Data
    public static class Answer {

        private Integer id;

        private String answer;
    }
}
