package com.ljz.compilationVSM.service.impl;

import com.ljz.compilationVSM.service.CodeBlockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CodeBlockServiceImpl implements CodeBlockService {
    @Override
    public String[] getComment(String language, String compLanguage, String method, String token) {
        return new String[0];
    }
}
