package com.ljz.compilationVSM.dependency.facade.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljz.compilationVSM.common.exception.BizException;
import com.ljz.compilationVSM.common.exception.BizExceptionCodeEnum;
import com.ljz.compilationVSM.dependency.convert.AiModelOptimCodeMapping;
import com.ljz.compilationVSM.dependency.dto.AiOptimCodeDTO;
import com.ljz.compilationVSM.dependency.dto.LLMRequest;
import com.ljz.compilationVSM.dependency.facade.AiModelFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Slf4j
@Component
public class AiModelFacadeImpl implements AiModelFacade {

    @Autowired
    @Qualifier("aiModelWebClient")
    private WebClient webClient;
    @Autowired
    private AiModelOptimCodeMapping aiModelOptimCodeMapping;

    @Value("${ai-model.freeQA}")
    private String freeQAPath;
    @Value("${ai-model.optimize}")
    private String optimPath;

    public String askByMessage(String question) {
//        AiAskByMessageRequestDTO requestDTO = new AiAskByMessageRequestDTO(question);
//        String requestBody = object2JsonString(requestDTO);
//        Mono<BaseResponseDTO<String>> response = postRequest(freeQAPath,requestBody, String.class);
////        AtomicReference<String> responseData =null;
////        response.subscribe(result -> responseData.set(result.getData()));
//        Optional<String> data = Optional.ofNullable(response.block().getData());
//        if(data.isPresent()){
//            return data.get();
//        }else{
//            log.error("大模型平台返回数据为空");
//            //todo
////            throw new BizException("大模型平台返回数据为空");
//            return null;
//        }
        return null;
    }

    @Override
    public Flux<String> optimize(AiOptimCodeDTO optimCodeDTO) {
        return streamCompletion(optimCodeDTO.getCode());
    }

    private Flux<String> streamCompletion(String prompt) {

        LLMRequest llmRequest = new LLMRequest(prompt);

        return webClient.post()
                .uri("/chat/completions")
                .bodyValue(llmRequest)
                .accept(MediaType.TEXT_EVENT_STREAM) // 接受SSE流
                .retrieve()
                .bodyToFlux(String.class)
                .map(this::processResponse);
    }

    private String processResponse(String rawResponse) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(rawResponse);
            return rootNode
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();
        } catch (Exception e) {
            throw new BizException(BizExceptionCodeEnum.SERVER_ERROR);
        }
    }
}
