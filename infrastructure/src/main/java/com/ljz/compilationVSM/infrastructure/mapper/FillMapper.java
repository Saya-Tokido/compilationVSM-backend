package com.ljz.compilationVSM.infrastructure.mapper;

import com.ljz.compilationVSM.infrastructure.po.FillPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ljz
 * @since 2024-06-09 17:32:58
 */
public interface FillMapper extends BaseMapper<FillPO> {
    List<FillPO> getFill(int number);
}
