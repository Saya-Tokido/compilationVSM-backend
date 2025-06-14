package com.ljz.compilationVSM.dependency.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Value("${ai-model.baseurl}")
    private String aiModelUrl;
    @Bean(name = "aiModelWebClient")
    public WebClient aiModelWebClient() {

        return WebClient.builder()
                .baseUrl(aiModelUrl)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
    @Value("${remote-compiler.baseurl}")
    private String remoteCompilerUrl;

    @Bean(name = "remoteCompilerWebClient")
    public WebClient remoteCompilerWebClient() {

        return WebClient.builder()
                .baseUrl(remoteCompilerUrl)
                .build();
    }
}
