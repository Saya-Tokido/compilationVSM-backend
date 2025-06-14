package com.ljz.compilationVSM.dependency.facade.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljz.compilationVSM.common.exception.BizException;
import com.ljz.compilationVSM.common.exception.BizExceptionCodeEnum;
import com.ljz.compilationVSM.dependency.convert.AiModelOptimCodeMapping;
import com.ljz.compilationVSM.dependency.dto.AiAskByMessageRequestDTO;
import com.ljz.compilationVSM.dependency.dto.AiOptimCodeDTO;
import com.ljz.compilationVSM.dependency.dto.AiOptimCodeRequestDTO;
import com.ljz.compilationVSM.dependency.dto.base.BaseResponseDTO;
import com.ljz.compilationVSM.dependency.facade.AiModelFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

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
        AiAskByMessageRequestDTO requestDTO = new AiAskByMessageRequestDTO(question);
        String requestBody = object2JsonString(requestDTO);
        Mono<BaseResponseDTO<String>> response = postRequest(freeQAPath,requestBody, String.class);
//        AtomicReference<String> responseData =null;
//        response.subscribe(result -> responseData.set(result.getData()));
        Optional<String> data = Optional.ofNullable(response.block().getData());
        if(data.isPresent()){
            return data.get();
        }else{
            log.error("大模型平台返回数据为空");
            //todo
//            throw new BizException("大模型平台返回数据为空");
            return null;
        }
    }

    @Override
    public String optimize(AiOptimCodeDTO optimCodeDTO) {
        AiOptimCodeRequestDTO convertDTO = aiModelOptimCodeMapping.convert(optimCodeDTO);
        String requestBody = object2JsonString(convertDTO);
        Mono<BaseResponseDTO<String>> response = postRequest(optimPath,requestBody, String.class);
//        AtomicReference<String> responseData =null;
//        response.subscribe(result -> responseData.set(result.getData()));
        Optional<String> data = Optional.ofNullable(response.block().getData());
        if(data.isPresent()){
            return data.get();
        }else{
            log.error("大模型平台返回数据为空");
            //todo
//            throw new BizException("大模型平台返回数据为空");
            return null;
        }
    }

    /**
     * 对象转json字符串
     *
     * @param object
     * @return
     */
    private String object2JsonString(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            log.error("Object to json string error!", e);
            //todo
//            throw new BizException("Object to json string error!");
            return null;
        }
    }

    /**
     * post请求
     * @param uri
     * @param requestBody
     * @return
     */
    private <T> Mono<BaseResponseDTO<T>> postRequest(String uri, Object requestBody,Class<T> responseType) {
        return webClient.post()
                .uri(uri)
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        clientResponse -> {
                            log.error("Client error! {}", clientResponse);
                            //todo
//                            return Mono.error(new BizException("Client error!"));
                            return null;
                        }
                )
                .onStatus(
                        HttpStatusCode::is5xxServerError,
                        clientResponse -> {
                            log.error("Server error! {}", clientResponse);
                            //todo
                            return Mono.error(new BizException(BizExceptionCodeEnum.SERVER_ERROR));
//                            return Mono.error(new BizException("Server error!"));
                        }
                )
                .bodyToMono(new ParameterizedTypeReference<BaseResponseDTO<T>>() {})
                .flatMap(response -> Mono.just(response)
                        .flatMap(responseBody -> {
                            T data = responseBody.getData();
                            return Mono.just(response);
                        })
                );

    }
}
