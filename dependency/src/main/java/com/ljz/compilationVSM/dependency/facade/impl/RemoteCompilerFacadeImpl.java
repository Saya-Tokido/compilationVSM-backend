package com.ljz.compilationVSM.dependency.facade.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljz.compilationVSM.common.enums.FileextEnum;
import com.ljz.compilationVSM.common.enums.LanguageEnum;
import com.ljz.compilationVSM.common.enums.LanguageEnum2;
import com.ljz.compilationVSM.common.exception.BizException;
import com.ljz.compilationVSM.common.exception.BizExceptionCodeEnum;
import com.ljz.compilationVSM.dependency.dto.*;
import com.ljz.compilationVSM.dependency.facade.RemoteCompilerFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
public class RemoteCompilerFacadeImpl implements RemoteCompilerFacade {

    private final WebClient webClient;
    @Value("${remote-compiler.path}")
    private String compilePath;

    @Autowired
    RemoteCompilerFacadeImpl(@Qualifier("remoteCompilerWebClient") WebClient webClient){
        this.webClient=webClient;
    }


    @Override
    public CompilationOutputDTO compile2(CompilationInputDTO compilationInputDTO) {
        CompilationRequest2 compilationRequest = new CompilationRequest2(compilationInputDTO.getCode(), compilationInputDTO.getStdin(), null);
        Optional<LanguageEnum2> languageEnumOptional = Optional.ofNullable(LanguageEnum2.getByName(compilationInputDTO.getLanguage()));
        if(languageEnumOptional.isPresent()){
            compilationRequest.setType(languageEnumOptional.get().getType());
        }else{
            log.error("调用远程编译器编程语言不支持,编程语言名: {}",compilationInputDTO.getLanguage());
            //todo
            //            throw new BizException("调用远程编译器编程语言不支持");
        return null;
        }
        Mono<CompilationResponse2> responseMono = webClient.post()
                .uri(compilePath)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(compilationRequest)
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
                        }
                )
                .bodyToMono(CompilationResponse2.class);
        return responseMono.timeout(Duration.ofSeconds(20))
                .flatMap(response -> {
                    if (Objects.isNull(response)) {
                        //todo
//                        return Mono.error(new BizException("远程编译器平台返回数据为空"));
                        return null;
                    }
                    CompilationOutputDTO compilationOutputDTO = checkResponse2(response);
                    return Mono.just(compilationOutputDTO);
                })
                .doOnSuccess(compilationOutputDTO -> log.info("Compilation result: " + compilationOutputDTO.getOutput()))
                //todo
                .onErrorResume(error -> Mono.error(new BizException(BizExceptionCodeEnum.SERVER_ERROR))).block();

    }

    private CompilationOutputDTO checkResponse2(CompilationResponse2 data) {
        CompilationOutputDTO compilationOutputDTO = new CompilationOutputDTO();

        if(0==data.getCode()&&0==data.getData().getCode()){
            compilationOutputDTO.setCompilationError(Boolean.FALSE);
            compilationOutputDTO.setOutput(data.getData().getOutput());
        }else{
            compilationOutputDTO.setCompilationError(Boolean.TRUE);
            log.error("调用远程编译器执行出错,原因是 {}",compilationOutputDTO.getOutput());
            //todo
//            throw new BizException("调用远程编译器执行出错");
            return null;
        }
        return compilationOutputDTO;
    }
}
