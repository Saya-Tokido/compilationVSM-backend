package com.ljz.compilationVSM.api.iface.teacher;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.request.admin.StudentUserCreateRequest;

/**
 * 账号管理接口
 *
 * @author ljz
 * @since 2025-01-20
 */
public interface AccountManagerIface {

    /**
     *  添加单个学生用户
     *
     * @param request 请求
     * @return 成功
     */
    Response<Void> addStudentUser(StudentUserCreateRequest request);
}
