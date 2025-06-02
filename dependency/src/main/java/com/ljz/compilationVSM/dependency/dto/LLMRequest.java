package com.ljz.compilationVSM.dependency.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class LLMRequest {
    private String model;
    private List<Message> messages;
    private boolean stream;
    private Integer max_tokens;
    private Double temperature;

    @Setter
    @Getter
    private static class Message {
        private String role;
        private String content;
    }

    public LLMRequest(String content) {
        this.model = "deepseek-chat";
        Message system = new Message();
        system.setRole("system");
        system.setContent("You are a helpful code assistant.ONLY generate the remaining code continuation.Respond with pure code only,no explanations,markdown,code blocks.");
        Message user = new Message();
        user.setRole("user");
        user.setContent(content);
        this.messages = Arrays.asList(system, user);
        this.max_tokens = 10;
        this.temperature = 0.0;
        this.stream = false;
    }
}