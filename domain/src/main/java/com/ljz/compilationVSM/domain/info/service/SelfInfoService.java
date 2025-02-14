package com.ljz.compilationVSM.domain.info.service;

import com.ljz.compilationVSM.domain.info.dto.StudentSelfInfoResponseDTO;
import com.ljz.compilationVSM.domain.info.dto.TeacherSelfInfoResponseDTO;

/**
 * 用户基本信息服务
 *
 * @author ljz
 * @since 2025-02-07
 */
public interface SelfInfoService {

    /**
     * 获取教师用户基本信息
     *
     * @return 教师用户基本信息
     */
    TeacherSelfInfoResponseDTO getTeacherSelfInfo();

    /**
     * 获取学生用户基本信息
     *
     * @return 学生用户基本信息
     */
    StudentSelfInfoResponseDTO getStudentSelfInfo();

}
