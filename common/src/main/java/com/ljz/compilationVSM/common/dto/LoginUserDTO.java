package com.ljz.compilationVSM.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户登录信息DTO
 *
 * @author ljz
 * @since 2024-12-25
 */
@Getter
@Setter
public class LoginUserDTO {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户角色
     */
    private Integer role;

    /**
     * 登录时间戳
     */
    private Long loginTimestamp;
}
