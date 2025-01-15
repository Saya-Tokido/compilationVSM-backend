package com.ljz.compilationVSM.domain.ObjQuestion.service;

import com.ljz.compilationVSM.domain.ObjQuestion.dto.ObjCheckRequestDTO;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.ObjCheckResponseDTO;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.ObjQueryDTO;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.ObjResponseDTO;

public interface ObjQuestionService {
    /**
     * 获取客观题题目
     *
     * @param objQueryDTO 请求入参
     * @return 选择填空题题目
     */
    ObjResponseDTO getObjQuestion(ObjQueryDTO objQueryDTO);

    /**
     * 校验客观题答案
     *
     * @param requestDTO 请求入参
     * @return 校验结果
     */
    ObjCheckResponseDTO checkObjQuestion(ObjCheckRequestDTO requestDTO);

}
