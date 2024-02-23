package com.ljz.compilationVSM.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljz.compilationVSM.common.Result;
import com.ljz.compilationVSM.common.exception.BizException;
import com.ljz.compilationVSM.dao.UserMapper;
import com.ljz.compilationVSM.entity.User;
import com.ljz.compilationVSM.service.UserService;
import com.ljz.compilationVSM.util.TokenHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public String login(Map loginMap) throws BizException {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,loginMap.get("userName"))
                .eq(User::getPassword,loginMap.get("password"));
        User user = getOne(queryWrapper);
        if(user == null){
            log.info("login failed");
            throw new BizException(1001,"userName or password error!");
        }
        log.info("login successful");
        return TokenHandler.genAccessToken(user.getId());
    }

    @Override
    public void logged(String token)throws BizException {
        Long userId=TokenHandler.parseToken(token);
        if(Optional.ofNullable(getById(userId)).isEmpty()) {
            throw new BizException(1002, "User hasn't logged in!");
        }
    }
}
