package com.ljz.compilationVSM.api.iface.teacher;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.request.teacher.ObjScoreModifyRequest;
import com.ljz.compilationVSM.api.response.teacher.ObjAnswerInfoResponse;

/**
 * 学生客观题答题情况评阅接口
 *
 * @author ljz
 * @since 2025-01-30
 */
public interface ObjReviewIface {
    /**
     * 获取学生客观题答题情况
     *
     * @param number 学生学号
     * @return 客观题答题情况
     */
    Response<ObjAnswerInfoResponse> getObjAnswerInfo(String number);

    /**
     * 客观题调分
     *
     * @param request 请求
     * @return 成功
     */
    Response<Void> modifyObjGrade(ObjScoreModifyRequest request);
}
