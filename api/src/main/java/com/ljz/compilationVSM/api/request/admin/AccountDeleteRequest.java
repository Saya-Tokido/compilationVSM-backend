package com.ljz.compilationVSM.api.request.admin;

import lombok.Getter;
import lombok.Setter;

/**
 * 删除账号请求
 *
 * @author ljz
 * @since 2025-02-16
 */
@Getter
@Setter
public class AccountDeleteRequest {
    /**
     * 学生学号或教师工号
     */
    private String number;
}
