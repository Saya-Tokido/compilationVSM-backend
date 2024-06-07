package com.ljz.compilationVSM.domain.task;

import com.ljz.compilationVSM.common.dto.base.KeyValueDTO;
import com.ljz.compilationVSM.dto.FreeQADTO;
import com.ljz.compilationVSM.dto.OptimCodeDTO;

import java.util.List;

public interface AiQAService {
    /**
     * 自由问答
     * @param question
     * @return
     */
    public FreeQADTO askByMessage(String question);

    /**
     * 指定问题问答
     * @param key
     * @return
     */
    public String askByKey(String key);

    /**
     * 代码优化问答
     * @param code
     * @return
     */

    OptimCodeDTO optimize(String code);

    /**
     * 获取指定问答问题列表
     * @return
     */
    List<KeyValueDTO<String,String>> getQuestionList();
}
