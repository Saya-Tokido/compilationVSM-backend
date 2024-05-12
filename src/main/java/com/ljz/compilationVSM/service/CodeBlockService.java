package com.ljz.compilationVSM.service;

import com.ljz.compilationVSM.dto.MethodBodyDto;
import com.ljz.compilationVSM.dto.MethodNameDto;

import java.util.List;

public interface CodeBlockService {
    public String[] getComment(String language, String compLanguage, String method);

    List<MethodNameDto> getMethodName(String language, String compLanguage);

    MethodBodyDto getMethodBody(Integer methodId);

    String check(String code);
}
