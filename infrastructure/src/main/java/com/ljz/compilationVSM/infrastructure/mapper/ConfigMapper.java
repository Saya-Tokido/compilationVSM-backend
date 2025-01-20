package com.ljz.compilationVSM.infrastructure.mapper;

import com.ljz.compilationVSM.infrastructure.po.ConfigPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 配置表 Mapper 接口
 * </p>
 *
 * @author ljz
 * @since 2025-01-15 18:51:49
 */
public interface ConfigMapper extends BaseMapper<ConfigPO> {
    ConfigPO getConfig();
}
