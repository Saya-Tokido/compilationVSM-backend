package com.ljz.compilationVSM.domain.facade;

import com.ljz.compilationVSM.domain.dto.OptimCodeDTO;

public interface AiModelFacade {
    /**
     * 大模型自由问答接口
     * @param question
     * @return
     */
    public String askByMessage(String question);
    /**
     * 大模型代码优化接口
     */
    public String optimize(OptimCodeDTO optimCodeDTO);
}
