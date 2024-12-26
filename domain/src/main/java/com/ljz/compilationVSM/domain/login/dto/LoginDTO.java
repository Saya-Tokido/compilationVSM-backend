package com.ljz.compilationVSM.domain.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * application层传来的认证对象
 *
 * @author ljz
 * @since 2024-12-25
 */
@Getter
@Setter
@AllArgsConstructor
public class LoginDTO {

    /**
     * 用户名
     */
    String userName;

    /**
     * 密码明文
     */
    String password;
}
