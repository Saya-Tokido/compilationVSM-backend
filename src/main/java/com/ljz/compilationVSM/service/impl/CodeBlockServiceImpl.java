package com.ljz.compilationVSM.service.impl;

import com.ljz.compilationVSM.dao.CodeBlockMapper;
import com.ljz.compilationVSM.dto.MethodBodyDto;
import com.ljz.compilationVSM.dto.MethodNameDto;
import com.ljz.compilationVSM.entity.MethodBody;
import com.ljz.compilationVSM.entity.MethodName;
import com.ljz.compilationVSM.service.CodeBlockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class CodeBlockServiceImpl implements CodeBlockService {

    @Autowired
    private CodeBlockMapper codeBlockMapper;
    @Override
    public String[] getComment(String language, String compLanguage, String method) {
        return codeBlockMapper
                .getComment(language,compLanguage,method)
                .toArray(new String[0]);
    }

    @Override
    public List<MethodNameDto> getMethodName(String language, String compLanguage) {
        List<MethodName> methodNameList= codeBlockMapper.getMethodName(language,compLanguage);
        List<MethodNameDto> dtoList=new ArrayList<>();
        methodNameList.forEach(item->{
            MethodNameDto dto=new MethodNameDto();
            dto.setId(item.getId());
            dto.setName(item.getName());
            dto.setLevel(item.getLevel().getStr());
            dto.setCommitNum(item.getCommitNum());
            double percent= (double)item.getPassNum()/ item.getCommitNum();
            dto.setPassPercent(String.format("%.2f%%", percent * 100));
            dtoList.add(dto);
        });
        return dtoList;
    }

    @Override
    public MethodBodyDto getMethodBody(Integer methodId) {
        MethodBody methodBody=codeBlockMapper.getMethodBody(methodId);
        MethodBodyDto dto=new MethodBodyDto(
                methodBody.getId(),
                methodBody.getDescription(),
                methodBody.getInput(),
                methodBody.getOutput(),
                methodBody.getInParam(),
                methodBody.getOutParam(),
                methodBody.getBody());
        return dto;
    }

    @Override
    public String check(String code) {
        return "passed";
    }
}
