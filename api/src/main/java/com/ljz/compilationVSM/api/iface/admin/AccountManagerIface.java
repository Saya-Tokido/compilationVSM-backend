package com.ljz.compilationVSM.api.iface.admin;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.request.admin.AccountDeleteRequest;
import com.ljz.compilationVSM.api.request.admin.StudentPageQueryRequest;
import com.ljz.compilationVSM.api.request.admin.StudentUserCreateRequest;
import com.ljz.compilationVSM.api.request.admin.TeacherUserCreateRequest;
import com.ljz.compilationVSM.api.response.admin.StudentInfoPageResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * 账号管理接口
 *
 * @author ljz
 * @since 2025-01-20
 */
public interface AccountManagerIface {

    /**
     * 添加单个学生用户
     *
     * @param request 请求
     * @return 成功
     */
    Response<Void> addStudentUser(StudentUserCreateRequest request);

    /**
     * 添加单个教师用户
     *
     * @param request 请求
     * @return 成功
     */
    Response<Void> addTeacherUser(TeacherUserCreateRequest request);

    /**
     * 通过excel批量添加学生
     *
     * @param file excel文件
     * @return 成功
     */
    Response<Void> addStudentByExcel(MultipartFile file);

    /**
     * 分页查询学生信息
     *
     * @param request 请求参数
     * @return 学生信息分页
     */
    Response<StudentInfoPageResponse> pageQueryStudent(StudentPageQueryRequest request);

    /**
     * 删除学生账号
     *
     * @param request 请求参数
     * @return 成功
     */
    Response<Void> deleteStudent(AccountDeleteRequest request);

    /**
     * 删除教师账号
     *
     * @param request 请求参数
     * @return 成功
     */
    Response<Void> deleteTeacher(AccountDeleteRequest request);
}
