package com.ljz.compilationVSM.api.iface.teacher;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.response.teacher.TeacherSelfInfoResponse;

/**
 * 当前用户基本信息接口
 *
 * @author ljz
 * @since 2025-02-07
 */
public interface TeacherSelfInfoIface {

    /**
     * 获取当前用户基本信息
     *
     * @return 教师用户基本信息
     */
    Response<TeacherSelfInfoResponse> getSelfInfo();
}
