package com.ljz.compilationVSM.infrastructure.mapper;

import com.ljz.compilationVSM.infrastructure.po.ChoosePO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ljz
 * @since 2024-12-02 15:12:10
 */
public interface ChooseMapper extends BaseMapper<ChoosePO> {
    List<ChoosePO> getChoose(int number);
}
