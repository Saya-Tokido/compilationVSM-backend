package com.ljz.compilationVSM.infrastructure.mapper;

import com.ljz.compilationVSM.infrastructure.po.UserPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ljz
 * @since 2024-12-02 21:28:15
 */
public interface UserMapper extends BaseMapper<UserPO> {
    /**
     * 根据指定条件查询用户
     *
     * @param queryDTO 查询参数
     * @return 用户信息
     */
    UserPO getUser(UserPO queryDTO);
}
