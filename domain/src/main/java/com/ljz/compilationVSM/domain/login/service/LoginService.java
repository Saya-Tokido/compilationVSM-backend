package com.ljz.compilationVSM.domain.login.service;

import com.ljz.compilationVSM.domain.login.dto.LoggedDTO;
import com.ljz.compilationVSM.domain.login.dto.LoginDTO;

/**
 * 登录服务
 *
 * @author ljz
 * @since 2024-12-25
 */
public interface LoginService {

    /**
     * 登录
     *
     * @param loginDTO 登录DTO
     * @return 用户登录结果
     */
    LoggedDTO login(LoginDTO loginDTO);

    /**
     * 登出
     */
    void logout();
}
