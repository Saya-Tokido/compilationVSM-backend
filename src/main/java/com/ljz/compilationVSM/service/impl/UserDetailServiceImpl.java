package com.ljz.compilationVSM.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljz.compilationVSM.common.exception.BizException;
import com.ljz.compilationVSM.dao.UserMapper;
import com.ljz.compilationVSM.dto.LoginUser;
import com.ljz.compilationVSM.entity.User;
import com.ljz.compilationVSM.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserDetailServiceImpl  extends ServiceImpl<UserMapper, User> implements UserDetailsService, UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException,BizException {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
        User user = getOne(queryWrapper);
        if(Optional.ofNullable(user).isEmpty()) {
            log.info("login failed");
            throw new BizException(1001, "userName or password error!");
        }
//        List<String> perms=userMapper.selectPermsById(user.getId());
        return new LoginUser(user,null);
    }
}
