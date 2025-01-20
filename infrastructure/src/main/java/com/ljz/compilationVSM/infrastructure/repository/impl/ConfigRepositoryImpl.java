package com.ljz.compilationVSM.infrastructure.repository.impl;

import com.ljz.compilationVSM.infrastructure.po.ConfigPO;
import com.ljz.compilationVSM.infrastructure.mapper.ConfigMapper;
import com.ljz.compilationVSM.infrastructure.repository.ConfigRepository;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 配置表 服务实现类
 * </p>
 *
 * @author ljz
 * @since 2025-01-15 18:51:49
 */
@Service
public class ConfigRepositoryImpl extends ServiceImpl<ConfigMapper, ConfigPO> implements ConfigRepository {

}
