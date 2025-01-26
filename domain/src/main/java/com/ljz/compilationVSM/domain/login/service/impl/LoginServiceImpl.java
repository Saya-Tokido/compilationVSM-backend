package com.ljz.compilationVSM.domain.login.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ljz.compilationVSM.common.exception.BizException;
import com.ljz.compilationVSM.common.exception.BizExceptionCodeEnum;
import com.ljz.compilationVSM.common.utils.*;
import com.ljz.compilationVSM.domain.login.dto.LoggedDTO;
import com.ljz.compilationVSM.domain.login.dto.LoginDTO;
import com.ljz.compilationVSM.common.dto.LoginUserDTO;
import com.ljz.compilationVSM.domain.login.service.LoginService;
import com.ljz.compilationVSM.infrastructure.po.UserPO;
import com.ljz.compilationVSM.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;


/**
 * 登出服务实现类
 *
 * @author ljz
 * @since 2024-12-25
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {


    private final UserRepository userRepository;
    private final RedisUtil redisUtil;
    private final BCryptUtil bCryptUtil;
    private final SnowflakeIdGenerator idGenerator;

    @Value("${redis-key-prefix.login-info}")
    private String loginInfoPrefix;

    @Value("${redis-key-prefix.login-record}")
    private String loginRecordPrefix;

    @Value("${expire-time.login-info}")
    private Long loginInfoExpire;

    @Value("${expire-time.login-record}")
    private Long loginRecordExpire;

    @Override
    public LoggedDTO login(LoginDTO loginDTO) {
        // 查询登录信息
        LambdaQueryWrapper<UserPO> queryWrapper = Wrappers.<UserPO>lambdaQuery()
                .select(UserPO::getId, UserPO::getUserName, UserPO::getRole, UserPO::getPassword)
                .eq(UserPO::getIsDelete, Boolean.FALSE)
                .eq(UserPO::getUserName, loginDTO.getUserName());
        UserPO userPO = userRepository.getOne(queryWrapper);
        if (Objects.isNull(userPO) || !bCryptUtil.checkMessage(loginDTO.getPassword(), userPO.getPassword())) {
            log.info("用户登录，用户名或密码错误");
            throw new BizException(BizExceptionCodeEnum.USERNAME_OR_PASSWORD_ERROR);
        }
        // 判断是否重复登录 (用户登录记录表和登录信息表，其中登录信息表比登录记录表早过期)
        clearLoginInfoAndRecord(userPO.getId().toString());
        // 登录操作
        long loginId = idGenerator.generate();
        // 存入redis
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        loginUserDTO.setUserId(userPO.getId());
        loginUserDTO.setUserName(userPO.getUserName());
        loginUserDTO.setRole(userPO.getRole());
        loginUserDTO.setLoginTimestamp(System.currentTimeMillis());
        redisUtil.stringExpirePut(loginRecordPrefix + userPO.getId().toString(), String.valueOf(loginId), loginRecordExpire);
        redisUtil.loginUserExpirePut(loginInfoPrefix + loginId, loginUserDTO, loginInfoExpire);
        // 生成token
        LoggedDTO loggedDTO = new LoggedDTO();
        loggedDTO.setToken(TokenHandler.genAccessToken(loginId));
        loggedDTO.setRoleId(userPO.getRole());
        return loggedDTO;
    }

    @Override
    public void logout() {
        clearLoginInfoAndRecord(UserContextHolder.getUserId().toString());
    }

    /**
     * 删除登录信息和记录
     *
     * @param userId 用户Id
     */
    private void clearLoginInfoAndRecord(String userId) {
        if (redisUtil.stringKeyExists(loginRecordPrefix + userId)) {
            Optional<String> loginIdOptional = Optional.ofNullable(redisUtil.stringValueGet(loginRecordPrefix + userId));
            if (loginIdOptional.isPresent()) {
                // 先删除登录信息再删除登录记录，登录信息存在是登录记录存在的充分不必要条件
                redisUtil.loginUserDelete(loginInfoPrefix + loginIdOptional.get());
                redisUtil.stringDelete(loginRecordPrefix + userId);
            }
            // 由于登录信息总比登录记录早过期，如果登录记录不存在，则无需后续处理
        }
    }
}
