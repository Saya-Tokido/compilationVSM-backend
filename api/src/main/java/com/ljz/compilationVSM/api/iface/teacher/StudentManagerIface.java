package com.ljz.compilationVSM.api.iface.teacher;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.request.teacher.StudentPageQueryRequest;
import com.ljz.compilationVSM.api.response.teacher.StudentInfoPageResponse;

/**
 * 学生信息管理接口
 *
 * @author ljz
 * @since 2025-01-27
 */
public interface StudentManagerIface {

    /**
     * 分页查询学生信息
     *
     * @param request 查询请求
     * @return 查询结果
     */
    Response<StudentInfoPageResponse> pageQueryStudentInfo(StudentPageQueryRequest request);

}
