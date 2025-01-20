package com.ljz.compilationVSM.domain.login.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 登录成功响应结果
 *
 * @author ljz
 * @since 2025-01-20
 */
@Getter
@Setter
public class LoggedDTO {
    /**
     * token
     */
    String token;

    /**
     * 角色id
     */
    Integer roleId;
}