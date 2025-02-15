package com.ljz.compilationVSM.api.iface.admin;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.request.admin.StudentUserCreateRequest;
import com.ljz.compilationVSM.api.request.admin.TeacherUserCreateRequest;
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
}
