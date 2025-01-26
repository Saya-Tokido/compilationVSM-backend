package com.ljz.compilationVSM.domain.account.service;

import com.ljz.compilationVSM.domain.account.dto.StudentUserCreateRequestDTO;

/**
 * 账户管理服务接口
 *
 * @author ljz
 * @since 2025-01-20
 */
public interface AccountManagerService {

    /**
     * 添加单个学生用户
     * @param requestDTO 请求参数
     */
    void addStudentUser(StudentUserCreateRequestDTO requestDTO);
}
