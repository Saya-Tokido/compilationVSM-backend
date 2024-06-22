package com.ljz.compilationVSM.domain.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
/**
 * application层传来的认证对象
 */
public class LoginDTO {
    String userName;
    String password;
}
