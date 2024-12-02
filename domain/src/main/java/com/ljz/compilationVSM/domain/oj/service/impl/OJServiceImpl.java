package com.ljz.compilationVSM.domain.oj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ljz.compilationVSM.domain.convert.OJDTOMapping;
import com.ljz.compilationVSM.domain.oj.dto.MethodBodyResponseDTO;
import com.ljz.compilationVSM.domain.oj.dto.MethodListRequestDTO;
import com.ljz.compilationVSM.domain.oj.dto.MethodResponseDTO;
import com.ljz.compilationVSM.domain.oj.service.OJService;
import com.ljz.compilationVSM.infrastructure.po.MethodBodyPO;
import com.ljz.compilationVSM.infrastructure.po.MethodNamePO;
import com.ljz.compilationVSM.infrastructure.repository.MethodBodyRepository;
import com.ljz.compilationVSM.infrastructure.repository.MethodNameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * OJ服务类
 * @author ljz
 * @since 2024-12-01
 */
@Service
@AllArgsConstructor
public class OJServiceImpl implements OJService {

    private final MethodNameRepository methodNameRepository;
    private final MethodBodyRepository methodBodyRepository;
    private final OJDTOMapping ojConvert;

    @Override
    public List<MethodResponseDTO> getMethodList(MethodListRequestDTO requestDTO) {
        LambdaQueryWrapper<MethodNamePO> queryWrapper = Wrappers.<MethodNamePO>lambdaQuery()
                .select(MethodNamePO::getId, MethodNamePO::getName, MethodNamePO::getLevel, MethodNamePO::getCommitNum, MethodNamePO::getPassNum)
                .eq(MethodNamePO::getIsDelete, Boolean.FALSE)
                .eq(MethodNamePO::getLanguage, requestDTO.getLanguage())
                .eq(MethodNamePO::getCompLanguage, requestDTO.getCompLanguage());
        return ojConvert.methodListConvert(methodNameRepository.list(queryWrapper));
    }

    @Override
    public MethodBodyResponseDTO getMethodBody(String methodId) {
        LambdaQueryWrapper<MethodBodyPO> queryWrapper = Wrappers.<MethodBodyPO>lambdaQuery()
                .select(MethodBodyPO::getId, MethodBodyPO::getDescription, MethodBodyPO::getInput, MethodBodyPO::getOutput, MethodBodyPO::getInParam, MethodBodyPO::getOutParam, MethodBodyPO::getBody)
                .eq(MethodBodyPO::getIsDelete, Boolean.FALSE)
                .eq(MethodBodyPO::getMethodId, Long.parseLong(methodId));
        return ojConvert.methodBodyConvert(methodBodyRepository.getOne(queryWrapper));
    }
}
