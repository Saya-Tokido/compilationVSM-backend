package com.ljz.compilationVSM.dependency.facade;

import com.ljz.compilationVSM.dependency.dto.AiOptimCodeDTO;

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
    public String optimize(AiOptimCodeDTO aioptimCodeDTO);
}
