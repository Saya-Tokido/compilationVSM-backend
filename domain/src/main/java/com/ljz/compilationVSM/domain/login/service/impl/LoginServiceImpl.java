package com.ljz.compilationVSM.domain.login.service.impl;


import com.ljz.compilationVSM.common.exception.BizException;
import com.ljz.compilationVSM.domain.login.dto.LoggedDTO;
import com.ljz.compilationVSM.domain.login.dto.LoginDTO;
import com.ljz.compilationVSM.domain.login.dto.LoginUserDTO;
import com.ljz.compilationVSM.domain.login.service.LoginService;
import com.ljz.compilationVSM.domain.utils.TokenHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {


    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    @Qualifier("loginRedisTemplate")
    private RedisTemplate redisTemplate;

    @Override
    public LoggedDTO login(LoginDTO loginDTO) throws BizException {



        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUserName(),loginDTO.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        if(Optional.ofNullable(authenticate).isEmpty()){
            throw new BizException(1001,"userName or password error!");
        }

        LoginUserDTO loginUserDTO = (LoginUserDTO) authenticate.getPrincipal();
        Long userid = loginUserDTO.getUserPO().getId();
        String token = TokenHandler.genAccessToken(userid);
        redisTemplate.opsForHash().put("login",userid.toString(),loginUserDTO);
        return new LoggedDTO(token);
    }

    @Override
    public String logout() {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUserDTO loginUserDTO = (LoginUserDTO) authentication.getPrincipal();
        Long userid = loginUserDTO.getUserPO().getId();
        redisTemplate.opsForHash().delete("login",userid.toString());
        return "Log out Success!";
    }
}
