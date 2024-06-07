package com.ljz.compilationVSM.dependency;

import com.ljz.compilationVSM.domain.facade.AiModelFacade;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Component
public class AiModel implements AiModelFacade {

    @Value("${ai-model.url}")
    private String url;
    public String post(String question){
        String url="";
        RestTemplate client =new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        params.set("message",question);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        ResponseEntity<HashMap> response = client.exchange(url, HttpMethod.POST, requestEntity, HashMap.class);
        return (String) response.getBody().get("generated_text");
    }
}
