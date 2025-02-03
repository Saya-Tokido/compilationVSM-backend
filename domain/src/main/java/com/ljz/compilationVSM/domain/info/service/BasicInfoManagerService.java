package com.ljz.compilationVSM.domain.info.service;

import com.ljz.compilationVSM.domain.info.dto.StudentInfoPageResponseDTO;
import com.ljz.compilationVSM.domain.info.dto.StudentPageQueryRequestDTO;

/**
 * 基本信息管理服务
 *
 * @author ljz
 * @since 2025-01-27
 */
public interface BasicInfoManagerService {

    /**
     * 分页查询学生基本信息
     *
     * @param requestDTO 请求参数DTO
     * @return 学生基本信息分页
     */
    StudentInfoPageResponseDTO pageQueryStudentInfo(StudentPageQueryRequestDTO requestDTO);
}
