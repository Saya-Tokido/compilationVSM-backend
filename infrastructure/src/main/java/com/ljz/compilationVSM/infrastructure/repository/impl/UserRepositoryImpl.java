package com.ljz.compilationVSM.infrastructure.repository.impl;

import com.ljz.compilationVSM.infrastructure.po.UserPO;
import com.ljz.compilationVSM.infrastructure.mapper.UserMapper;
import com.ljz.compilationVSM.infrastructure.repository.UserRepository;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ljz
 * @since 2024-12-02 15:12:10
 */
@Service
public class UserRepositoryImpl extends ServiceImpl<UserMapper, UserPO> implements UserRepository {

}
