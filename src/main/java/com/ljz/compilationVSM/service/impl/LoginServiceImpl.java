package com.ljz.compilationVSM.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljz.compilationVSM.common.exception.BizException;
import com.ljz.compilationVSM.dao.UserMapper;
import com.ljz.compilationVSM.entity.User;
import com.ljz.compilationVSM.dto.LoginUser;
import com.ljz.compilationVSM.service.LoginService;
import com.ljz.compilationVSM.service.UserService;
import com.ljz.compilationVSM.util.TokenHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {


    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String login(User user) throws BizException {



        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        if(Optional.ofNullable(authenticate).isEmpty()){
            throw new BizException(1001,"userName or password error!");
        }

        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        Long userid = loginUser.getUser().getId();
        String token = TokenHandler.genAccessToken(userid);
        redisTemplate.opsForHash().put("login",userid.toString(),loginUser);
        return token;

    }

    @Override
    public void logout() {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getUser().getId();
        redisTemplate.opsForHash().delete("login",userid.toString());
    }
}
