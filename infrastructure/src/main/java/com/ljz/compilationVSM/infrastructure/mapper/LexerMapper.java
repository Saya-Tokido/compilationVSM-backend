package com.ljz.compilationVSM.infrastructure.mapper;

import com.ljz.compilationVSM.infrastructure.po.LexerPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 词法分析器题目表 Mapper 接口
 * </p>
 *
 * @author ljz
 * @since 2024-12-04 16:12:22
 */
public interface LexerMapper extends BaseMapper<LexerPO> {

    /**
     * 更新词法分析器题
     *
     * @return 更新数量
     */
    Integer updateLexer(LexerPO lexerPO);
}
