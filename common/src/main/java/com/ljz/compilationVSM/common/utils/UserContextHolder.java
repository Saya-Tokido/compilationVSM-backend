package com.ljz.compilationVSM.common.utils;

import com.ljz.compilationVSM.common.dto.LoginUserDTO;

import java.util.Map;

/**
 * 用户登录信息上下文
 *
 * @author ljz
 * @since 2024-12-26
 */
public class UserContextHolder {
    private static final ThreadLocal<LoginUserDTO> userContext = new ThreadLocal<>();

    /**
     * 存储当前用户信息
     *
     * @param userInfo 当前用户信息
     */
    public static void setUser(LoginUserDTO userInfo) {
        userContext.set(userInfo);
    }

    /**
     * 获取当前用户Id
     *
     * @return 当前用户Id
     */
    public static Long getUserId(){
        return userContext.get().getUserId();
    }

    /**
     * 获取当前用户角色
     *
     * @return 当前用户角色
     */
    public static Integer getRole(){
        return userContext.get().getRole();
    }

    /**
     * 防止ThreadLocal内存泄露和线程池安全问题
     */
    public static void clear() {
        userContext.remove();
    }
}