package com.ljz.compilationVSM.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ljz.compilationVSM.dao.CodeBlockMapper;
import com.ljz.compilationVSM.service.CodeBlockService;
import com.ljz.compilationVSM.service.UserService;
import com.ljz.compilationVSM.util.TokenHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
public class CodeBlockServiceImpl implements CodeBlockService {

    @Autowired
    private UserService userService;
    @Autowired
    private CodeBlockMapper codeBlockMapper;
    @Override
    public String[] getComment(String language, String compLanguage, String method) {
        return codeBlockMapper
                .getComment(language,compLanguage,method)
                .toArray(new String[0]);
    }
}
