package com.ljz.compilationVSM.domain.aiQA.service;

import com.ljz.compilationVSM.common.dto.base.KeyValueDTO;
import com.ljz.compilationVSM.domain.aiQA.dto.*;
import reactor.core.publisher.Flux;

import java.util.List;

public interface AiQAService {
    /**
     * 自由问答
     * @param qaQuestionDTO
     * @return
     */
    public FreeQAAnswerDTO askByMessage(FreeQAQuestionDTO qaQuestionDTO);

    /**
     * 指定问题问答
     * @param key
     * @return
     */
    public SpecificAnswerDTO askByKey(SpecificQuestionDTO key);

    /**
     * 代码优化问答
     * @param optimCodeDTO
     * @return
     */

    Flux<String> optimize(OptimCodeDTO optimCodeDTO);

    /**
     * 获取指定问答问题列表
     * @return
     */
    List<KeyValueDTO<String,String>> getQuestionList();
}
