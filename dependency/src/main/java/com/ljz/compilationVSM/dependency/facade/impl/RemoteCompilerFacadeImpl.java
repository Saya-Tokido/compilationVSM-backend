package com.ljz.compilationVSM.dependency.facade.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljz.compilationVSM.common.enums.FileextEnum;
import com.ljz.compilationVSM.common.enums.LanguageEnum;
import com.ljz.compilationVSM.common.exception.BizException;
import com.ljz.compilationVSM.dependency.dto.CompilationInputDTO;
import com.ljz.compilationVSM.dependency.dto.CompilationOutputDTO;
import com.ljz.compilationVSM.dependency.dto.CompilationRequestDTO;
import com.ljz.compilationVSM.dependency.dto.CompilationResponse;
import com.ljz.compilationVSM.dependency.dto.base.BaseResponseDTO;
import com.ljz.compilationVSM.dependency.facade.RemoteCompilerFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
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
    @Value("${remote-compiler.token}")
    private String token;
    @Value("${remote-compiler.success}")
    private String successMessage;
    @Value("${remote-compiler.path}")
    private String compilePath;

    @Autowired
    RemoteCompilerFacadeImpl(@Qualifier("remoteCompilerWebClient") WebClient webClient){
        this.webClient=webClient;
    }

    @Override
    public CompilationOutputDTO compile(CompilationInputDTO compilationInputDTO) {
        CompilationRequestDTO compilationRequestDTO = new CompilationRequestDTO();
        compilationRequestDTO.setCode(compilationInputDTO.getCode());
        Optional<FileextEnum> fileextEnumOptional = Optional.ofNullable(FileextEnum.getByName(compilationInputDTO.getFileext()));
        if(fileextEnumOptional.isPresent()){
            compilationRequestDTO.setFileext(fileextEnumOptional.get().getName());
        }else{
            log.error("调用远程编译器文件格式不支持,格式名: {}",compilationInputDTO.getFileext());
            throw new BizException("调用远程编译器文件格式不支持");
        }
        Optional<LanguageEnum> languageEnumOptional = Optional.ofNullable(LanguageEnum.getByName(compilationInputDTO.getLanguage()));
        if(languageEnumOptional.isPresent()){
            compilationRequestDTO.setLanguage(languageEnumOptional.get().getCode());
        }else{
            log.error("调用远程编译器编程语言不支持,编程语言名: {}",compilationInputDTO.getLanguage());
            throw new BizException("调用远程编译器编程语言不支持");
        }
        compilationRequestDTO.setStdin(compilationInputDTO.getStdin());
        compilationRequestDTO.setToken(token);
        MultiValueMap<String, String> requestMap = object2MultiValueMap(compilationRequestDTO);
        Mono<CompilationResponse> responseMono = postRequest(compilePath,requestMap, CompilationResponse.class);
        return responseMono.timeout(Duration.ofSeconds(20))
                .flatMap(response -> {
                    if (Objects.isNull(response)) {
                        return Mono.error(new BizException("远程编译器平台返回数据为空"));
                    }
                    CompilationOutputDTO compilationOutputDTO = checkResponse(response);
                    return Mono.just(compilationOutputDTO);
                })
                .doOnSuccess(compilationOutputDTO -> log.info("Compilation result: " + compilationOutputDTO.getOutput()))
                .onErrorResume(error -> Mono.error(new BizException("未知错误," + error.getMessage()))).block();
    }

    /**
     * 校验编译器输出
     * @param data
     * @return
     */
    private CompilationOutputDTO checkResponse(CompilationResponse data) {
        CompilationOutputDTO compilationOutputDTO = new CompilationOutputDTO();
        // 配置文件解析有问题
        successMessage="\n\n";
        if(data.getErrors().equals(successMessage)){
            compilationOutputDTO.setOutput(data.getOutput());
            compilationOutputDTO.setCompilationError(false);
        }else{
            compilationOutputDTO.setOutput(data.getErrors());
            compilationOutputDTO.setCompilationError(true);
        }
        return compilationOutputDTO;
    }

    /**
     * 对象转json字符串
     *
     * @param object
     * @return
     */
    private MultiValueMap<String,String> object2MultiValueMap(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> map = objectMapper.convertValue(object, Map.class);
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            map.forEach(formData::add);
            return formData;
        } catch (Exception e) {
            log.error("Object to map error!", e);
            throw new BizException("Object to map error!");
        }
    }
    private Mono<CompilationResponse> postRequest(String uri, MultiValueMap<String, String> formData, Class<CompilationResponse> responseType) {
        return webClient.post()
                .uri(uri)
                .contentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        clientResponse -> {
                            log.error("Client error! {}", clientResponse);
                            return Mono.error(new BizException("Client error!"));
                        }
                )
                .onStatus(
                        HttpStatusCode::is5xxServerError,
                        clientResponse -> {
                            log.error("Server error! {}", clientResponse);
                            return Mono.error(new BizException("Server error!"));
                        }
                )
                .bodyToMono(responseType);
    }
}
