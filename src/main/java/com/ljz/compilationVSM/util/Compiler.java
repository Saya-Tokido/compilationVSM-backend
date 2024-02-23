package com.ljz.compilationVSM.util;

import com.ljz.compilationVSM.dto.CodeDto;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

public class Compiler {
    public static HashMap remoteCompiler(CodeDto codeDto){
        String code=codeDto.getCode();
        String stdin=codeDto.getStdin();
        String language=codeDto.getLanguage();
        String fileext=codeDto.getFileext();
        code=null==code?"#include <stdio.h>\n" +
                "int main()\n" +
                "{int i=99;\n" +
                "printf(\"%d\",i);\n" +
                "   return 0;\n" +
                "}":code;
        stdin=null==stdin?"":stdin;
        language=null==language?"7":language;
        fileext=null==fileext?"c":fileext;
        String url = "https://www.runoob.com/try/compile2.php";
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("token", "066417defb80d038228de76ec581a50a");
        params.add("stdin", stdin);
        params.add("language", language);
        params.add("fileext", fileext);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        ResponseEntity<HashMap> response = client.exchange(url, HttpMethod.POST, requestEntity, HashMap.class);
        return response.getBody();
    }
}
