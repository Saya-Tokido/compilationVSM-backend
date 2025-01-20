package com.ljz.compilationVSM.api.response.base;

import lombok.Getter;
import lombok.Setter;

/**
 * 登录响应
 *
 * @author ljz
 * @since 2025-01-20
 */
@Getter
@Setter
public class LoginResponse {
    /**
     * token
     */
    String token;

    /**
     * 角色id
     */
    Integer roleId;
}
