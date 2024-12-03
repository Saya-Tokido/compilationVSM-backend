package com.ljz.compilationVSM.domain.oj.service;

import com.ljz.compilationVSM.domain.oj.dto.CodeReviewResponseDTO;
import com.ljz.compilationVSM.domain.oj.dto.MethodBodyResponseDTO;
import com.ljz.compilationVSM.domain.oj.dto.MethodListRequestDTO;
import com.ljz.compilationVSM.domain.oj.dto.MethodResponseDTO;

import java.util.List;

/**
 * 代码评估服务
 * @author ljz
 * @since 2024-12-02
 */
public interface OJService {

    /**
     * 获取函数列表
     * @param requestDTO 请求DTO
     * @return 函数列表
     */
    List<MethodResponseDTO> getMethodList(MethodListRequestDTO requestDTO);

    /**
     * 获取函数体
     * @param id 函数体id
     * @return 函数体
     */
    MethodBodyResponseDTO getMethodBody(Long id);

    /**
     * 校验代码
     * @param methodId 函数名id
     * @param code 待校验代码
     * @return  校验反馈
     */
    CodeReviewResponseDTO checkCode(Long methodId,String code);

}
