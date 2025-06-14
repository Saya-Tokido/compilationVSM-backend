package com.ljz.compilationVSM.infrastructure.mapper;

import com.ljz.compilationVSM.infrastructure.po.LexerAnswerPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljz.compilationVSM.infrastructure.queryDTO.LexerPDCodeQueryDTO;

import java.util.List;

/**
 * <p>
 * 词法分析器题成绩表 Mapper 接口
 * </p>
 *
 * @author ljz
 * @since 2024-12-25 09:58:21
 */
public interface LexerAnswerMapper extends BaseMapper<LexerAnswerPO> {
    void updateLexerAnswer(LexerAnswerPO lexerAnswerPO);

    /**
     * 查重代码查询
     *
     * @param queryDTO 查询参数
     * @return 查重代码id列表
     */
    List<Long> getPDCodeId(LexerPDCodeQueryDTO queryDTO);
}
