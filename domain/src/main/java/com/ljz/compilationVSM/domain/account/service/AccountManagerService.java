package com.ljz.compilationVSM.domain.account.service;

import com.ljz.compilationVSM.domain.account.dto.StudentUserCreateRequestDTO;
import com.ljz.compilationVSM.domain.account.dto.TeacherUserCreateRequestDTO;

import java.util.List;

/**
 * 账户管理服务接口
 *
 * @author ljz
 * @since 2025-01-20
 */
public interface AccountManagerService {

    /**
     * 添加单个学生用户
     *
     * @param requestDTO 请求参数
     */
    void addStudentUser(StudentUserCreateRequestDTO requestDTO);

    /**
     * 添加单个教师用户
     *
     * @param requestDTO 请求参数
     */
    void addTeacherUser(TeacherUserCreateRequestDTO requestDTO);

    void addStudentUserBatch(List<StudentUserCreateRequestDTO> requestDTO);

    /**
     * 删除学生账号
     *
     * @param number 学生学号
     */
    void deleteStudentAccount(String number);

    /**
     * 删除教师账号
     *
     * @param number 教师工号
     */
    void deleteTeacherAccount(String number);
}
