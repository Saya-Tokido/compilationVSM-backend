//package com.ljz.compilationVSM.domain.login.service.impl;
//
//import com.ljz.compilationVSM.common.dto.LoginUserDTO;
//import com.ljz.compilationVSM.domain.login.service.AuthenticationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AuthenticationServiceImpl implements AuthenticationService {
//
//    @Autowired
//    @Qualifier("loginRedisTemplate")
//    private RedisTemplate redisTemplate;
//
//    @Override
//    public LoginUserDTO getLoginUserDTO(String userId) {
//        return (LoginUserDTO) redisTemplate.opsForHash().get("login", userId);
//    }
//}
