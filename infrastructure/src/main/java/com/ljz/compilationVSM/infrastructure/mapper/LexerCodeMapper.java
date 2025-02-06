package com.ljz.compilationVSM.infrastructure.mapper;

import com.ljz.compilationVSM.infrastructure.po.LexerCodePO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 词法分析器代码表 Mapper 接口
 * </p>
 *
 * @author ljz
 * @since 2024-12-25 09:58:21
 */
public interface LexerCodeMapper extends BaseMapper<LexerCodePO> {

    List<LexerCodePO> getBestCode(Long lexerId, Integer number);
}
