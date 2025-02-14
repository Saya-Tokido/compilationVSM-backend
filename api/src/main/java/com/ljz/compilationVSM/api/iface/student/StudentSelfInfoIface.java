package com.ljz.compilationVSM.api.iface.student;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.response.student.StudentSelfInfoResponse;

/**
 * 当前用户基本信息接口
 *
 * @author ljz
 * @since 2025-02-07
 */
public interface StudentSelfInfoIface {

    /**
     * 获取当前用户基本信息
     *
     * @return 学生用户基本信息
     */
    Response<StudentSelfInfoResponse> getSelfInfo();
}
